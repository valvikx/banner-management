package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.BaseIntegrationTest;
import by.valvik.bannermanagement.domain.Banner;
import by.valvik.bannermanagement.domain.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BannerControllerTest extends BaseIntegrationTest {

    @Autowired
    public BannerControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {

        super(mockMvc, objectMapper);

    }

    @Test
    void getOneExpectStatusOk() throws Exception {

        getMockMvc().perform(get("/api/banners/1").with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("First humor banner"))
                    .andExpect(jsonPath("$.price").value(150.00))
                    .andExpect(jsonPath("$.content").value("first humor banner content 150.00"));
        
    }

    @Test
    void getOneWhenBannerDoesNotExist() throws Exception {

        getMockMvc().perform(get("/api/banners/100").with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: 100"))
                    .andExpect(jsonPath("$.message").value("Banner not found"));

    }

    @Test
    void getOneWithoutAuthenticate() throws Exception {

        getMockMvc().perform(get("/api/banners/1"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Authentication is required to access this resource"));

    }

    @Test
    void create() throws Exception {

        Banner secondArcadeGamesBanner = Banner.builder()
                                               .name("Second arcade games banner")
                                               .price(new BigDecimal("200.00"))
                                               .category(Category.builder().name("Arcade games").build())
                                               .content("second arcade games banner content 200.00")
                                               .build();

        getMockMvc().perform(getPostRequestBuilder("/api/banners", secondArcadeGamesBanner).with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("Second arcade games banner"))
                    .andExpect(jsonPath("$.price").value(200.00))
                    .andExpect(jsonPath("$.category.name").value("Arcade games"))
                    .andExpect(jsonPath("$.content").value("second arcade games banner content 200.00"));

    }

    @Test
    void createWhenBannerCategoryIsNull() throws Exception {

        Banner secondArcadeGamesBanner = Banner.builder()
                                               .name("Second arcade games banner")
                                               .price(new BigDecimal("200.00"))
                                               .category(null)
                                               .content("second arcade games banner content 200.00")
                                               .build();

        getMockMvc().perform(getPostRequestBuilder("/api/banners", secondArcadeGamesBanner).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.category").value("Category is mandatory"));

    }

    @Test
    void createWhenBannerCategoryNameIsEmpty() throws Exception {

        Banner secondArcadeGamesBanner = Banner.builder()
                                               .name("Second arcade games banner")
                                               .price(new BigDecimal("200.00"))
                                               .category(Category.builder().name("").build())
                                               .content("second arcade games banner content 200.00")
                                               .build();

        getMockMvc().perform(getPostRequestBuilder("/api/banners", secondArcadeGamesBanner).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: "))
                    .andExpect(jsonPath("$.message").value("Category not found"));
        
    }

    @Test
    void createWhenBannerCategoryNameDoesNotExist() throws Exception {

        Banner secondArcadeGamesBanner = Banner.builder()
                                               .name("Second arcade games banner")
                                               .price(new BigDecimal("200.00"))
                                               .category(Category.builder().name("Car").build())
                                               .content("second arcade games banner content 200.00")
                                               .build();

        getMockMvc().perform(getPostRequestBuilder("/api/banners", secondArcadeGamesBanner).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: Car"))
                    .andExpect(jsonPath("$.message").value("Category not found"));

    }

    @Test
    void createWhenBannerAlreadyExists() throws Exception {

        Banner firstMusicBanner = Banner.builder()
                                        .name("First music banner")
                                        .price(new BigDecimal("100.00"))
                                        .category(Category.builder().name("Music").build())
                                        .content("first music banner content")
                                        .build();

        getMockMvc().perform(getPostRequestBuilder("/api/banners", firstMusicBanner).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").value("Banner Name already exists"));
        
    }

    @Test
    void update() throws Exception {

        Banner editedSecondMusicBanner = Banner.builder()
                                               .name("Second music banner v2")
                                               .price(new BigDecimal("65.00"))
                                               .category(Category.builder().name("Music").build())
                                               .content("second music banner content 70.00")
                                               .build();

        getMockMvc().perform(getPutRequestBuilder("/api/banners/5", editedSecondMusicBanner).with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.name").value("Second music banner v2"))
                    .andExpect(jsonPath("$.price").value(65.00))
                    .andExpect(jsonPath("$.category.name").value("Music"))
                    .andExpect(jsonPath("$.content").value("second music banner content 70.00"));

    }

    @Test
    void remove() throws Exception {

        getMockMvc().perform(delete("/api/banners/3").with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(3))
                    .andExpect(jsonPath("$.name").value("Third humor banner"))
                    .andExpect(jsonPath("$.price").value(70.00))
                    .andExpect(jsonPath("$.category.name").value("Humor"))
                    .andExpect(jsonPath("$.content").value("third humor banner content 70.00"));


    }

}