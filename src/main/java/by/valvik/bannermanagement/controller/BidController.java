package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.domain.Category;
import by.valvik.bannermanagement.domain.Request;
import by.valvik.bannermanagement.service.BannerService;
import by.valvik.bannermanagement.service.CategoryService;
import by.valvik.bannermanagement.service.GenericService;
import by.valvik.bannermanagement.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
@RequestMapping("api/bid")
public class BidController {

    private static final String CATEGORY_NAME_HEADER = "category-name";

    private final BannerService bannerService;

    private final CategoryService categoryService;

    private final GenericService<Request> requestService;

    public BidController(BannerService bannerService, CategoryService categoryService,
                         GenericService<Request> requestService) {

        this.bannerService = bannerService;

        this.categoryService = categoryService;

        this.requestService = requestService;

    }

    @GetMapping()
    @JsonView(View.Public.class)
    public ResponseEntity<Banner> getBannerContent(@RequestParam String category) {

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ipAddress = httpServletRequest.getRemoteAddr();

        String userAgent = httpServletRequest.getHeader(USER_AGENT);

        Optional<Banner> requestedCategoryBanner = bannerService.getRequestedCategoryBanner(category, userAgent, ipAddress);

        return requestedCategoryBanner.map(banner -> {

                                          Request request = Request.builder()
                                                  .banner(banner)
                                                  .userAgent(userAgent)
                                                  .ipAddress(ipAddress)
                                                  .date(LocalDateTime.now())
                                                  .build();

                                          requestService.create(request);

                                          return ResponseEntity.ok(banner);

                                     })
                                      .orElseGet(() -> {

                                          HttpHeaders headers = new HttpHeaders();

                                          Category requestedCategory = categoryService.getCategoryByReqName(category);

                                          headers.set(CATEGORY_NAME_HEADER, requestedCategory.getName());

                                          return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

                                      });

    }

}
