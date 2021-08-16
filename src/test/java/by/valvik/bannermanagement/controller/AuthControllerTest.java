package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.BaseIntegrationTest;
import by.valvik.bannermanagement.security.domain.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends BaseIntegrationTest {

    @Autowired
    public AuthControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {

        super(mockMvc, objectMapper);

    }

    @Test
    void authenticate() throws Exception {

        Admin admin = Admin.builder()
                           .login("admin")
                           .password("admin")
                           .build();

        getMockMvc().perform(getPostRequestBuilder("/api/auth", admin))
                    .andExpect(status().isOk())
                    .andExpect(header().exists(AUTHORIZATION));



    }

    @Test
    void failedAuthenticate() throws Exception {

        Admin invalidAdmin = Admin.builder()
                                  .login("admin")
                                  .password("user")
                                  .build();

        getMockMvc().perform(getPostRequestBuilder("/api/auth", invalidAdmin))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Login or password is invalid"));

    }

}