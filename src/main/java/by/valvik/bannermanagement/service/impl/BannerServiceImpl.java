package by.valvik.bannermanagement.service.impl;

import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.domain.Category;
import by.valvik.bannermanagement.error.ErrorAction;
import by.valvik.bannermanagement.repository.BannerRepository;
import by.valvik.bannermanagement.repository.CategoryRepository;
import by.valvik.bannermanagement.repository.RequestRepository;
import by.valvik.bannermanagement.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static by.valvik.bannermanagement.util.Reflections.getEntityName;

@Service
public class BannerServiceImpl extends GenericServiceImpl<Banner, BannerRepository>
                               implements BannerService {

    private static final Random RANDOM = new Random();

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    public BannerServiceImpl(BannerRepository bannerRepository, ErrorAction errorAction,
                             CategoryRepository categoryRepository, RequestRepository requestRepository) {

        super(bannerRepository, errorAction);

        this.categoryRepository = categoryRepository;

        this.requestRepository = requestRepository;
    }

    @Override
    public Banner create(Banner bannerFromClient) {

        Category bannerCategoryFromDb = getBannerCategory(bannerFromClient);

        bannerFromClient.setCategory(bannerCategoryFromDb);

        bannerFromClient.setIsDeleted(false);

        return getRepository().save(bannerFromClient);

    }

    @Override
    public Banner update(Integer id, Banner bannerFromClient) {

        Category bannerCategoryFromDb = getBannerCategory(bannerFromClient);

        Banner bannerFromDb = getAndCopy(id, bannerFromClient);

        bannerFromDb.setCategory(bannerCategoryFromDb);

        return getRepository().save(bannerFromDb);

    }

    @Override
    public Banner delete(Integer id) {

        Banner bannerFromDb = getEntityFromDb(id);

        bannerFromDb.setIsDeleted(true);

        getRepository().save(bannerFromDb);

        return bannerFromDb;

    }

    @Override
    public Optional<Banner> getRequestedCategoryBanner(String reqName, String userAgent, String ipAddress) {

        List<Integer> requestedBannerIds = requestRepository.findBannerIdsByUserAgentAndIpAddress(userAgent, ipAddress);

        List<Banner> categoryBanners = getRepository().findAllWithMaxPrice(reqName, requestedBannerIds);

        return categoryBanners.isEmpty() ? Optional.empty()
                                         : Optional.of(categoryBanners.get(RANDOM.nextInt(categoryBanners.size())));

    }

    private Category getBannerCategory(Banner banner) {

        Category bannerCategoryFromClient = banner.getCategory();

        String categoryName = bannerCategoryFromClient.getName();

        return categoryRepository.findByName(categoryName)
                                 .orElseThrow(getErrorAction().onNotFound(categoryName,
                                                                          getEntityName(bannerCategoryFromClient)));

    }

}
