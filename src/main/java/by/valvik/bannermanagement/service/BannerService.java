package by.valvik.bannermanagement.service;

import by.valvik.bannermanagement.domain.Banner;

import java.util.Optional;

public interface BannerService {

    Optional<Banner> getRequestedCategoryBanner(String reqName, String userAgent, String ipAddress);

}
