package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.service.BannerService;
import by.valvik.bannermanagement.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/banners")
public class BannerController extends BaseController<Banner, GenericService<Banner>> {

    public BannerController(GenericService<Banner> bannerService) {

        super(bannerService);

    }

}
