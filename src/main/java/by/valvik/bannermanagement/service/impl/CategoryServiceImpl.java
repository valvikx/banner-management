package by.valvik.bannermanagement.service.impl;

import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.domain.Category;
import by.valvik.bannermanagement.error.ErrorAction;
import by.valvik.bannermanagement.repository.BannerRepository;
import by.valvik.bannermanagement.repository.CategoryRepository;
import by.valvik.bannermanagement.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.valvik.bannermanagement.util.Reflections.getEntityName;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, CategoryRepository>
                                 implements CategoryService {

    private final BannerRepository bannerRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ErrorAction errorAction,
                               BannerRepository bannerRepository) {

        super(categoryRepository, errorAction);

        this.bannerRepository = bannerRepository;

    }

    @Override
    public Category create(Category categoryFromClient) {

        categoryFromClient.setIsDeleted(false);

        return getRepository().save(categoryFromClient);

    }

    @Override
    public Category delete(Integer id) {

        Category categoryFromDb = getEntityFromDb(id);

        List<Banner> relatedBanners = bannerRepository.findAllByCategoryAndIsDeletedFalse(categoryFromDb);

        if (!relatedBanners.isEmpty()) {

            List<Integer> bannerIds = relatedBanners.stream().map(Banner::getId).toList();

            getErrorAction().onCategoryDelete(bannerIds.toString());

        }

        categoryFromDb.setIsDeleted(true);

        return getRepository().save(categoryFromDb);

    }

    @Override
    public Category getCategoryByReqName(String reqName) {

        String entityName = getEntityName(this);

        return getRepository().findByReqName(reqName)
                              .orElseThrow(getErrorAction().onNotFound(reqName, entityName));

    }

}
