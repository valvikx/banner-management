package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.BaseIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BidControllerTest extends BaseIntegrationTest {

    @Autowired
    public BidControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {

        super(mockMvc, objectMapper);

    }

    @Test
    void getBannerContentForMusicAfterOneRequest() throws Exception {

        performGetRequest("music")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", endsWith("banner content 50.00")));

    }

    @Test
    void getBannerContentWithOneRecordInBanners() throws Exception {

        performGetRequest("arcade-games")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("first arcade games banner 250.00"));


    }

    @Test
    void getBannerContentForMusicAfterThreeRequests() throws Exception {

       performGetRequest("music")
               .andDo(result -> performGetRequest("music")
                                    .andExpect(status().isOk())
                                    .andExpect(jsonPath("$.content", endsWith("banner content 50.00"))))
               .andDo(result -> performGetRequest("music").andExpect(status().isNoContent()));

    }

    @Test
    void getBannerContentForHumorAfterThreeRequests() throws Exception {

        performGetRequest("humor")
                .andDo(result -> performGetRequest("humor")
                                    .andExpect(status().isOk())
                                    .andExpect(jsonPath("$.content", endsWith("banner content 150.00"))))
                .andDo(result -> performGetRequest("humor")
                                    .andExpect(status().isOk())
                                    .andExpect(jsonPath("$.content", endsWith("banner content 70.00"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", endsWith("banner content 150.00")));

    }

    private ResultActions performGetRequest(String reqName) throws Exception {

        return getMockMvc().perform(get("/api/bid?category={reqName}", reqName).with(remoteAddr())
                                                                                     .with(userAgent()));

    }

}