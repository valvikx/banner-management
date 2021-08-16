package by.valvik.bannermanagement;

import by.valvik.bannermanagement.security.domain.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
public abstract class BaseIntegrationTest extends BaseTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    public BaseIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {

        this.mockMvc = mockMvc;

        this.objectMapper = objectMapper;

    }

    public MockHttpServletRequestBuilder getPostRequestBuilder(String url, Object entity) {

        try {

            return post(url).content(objectMapper.writeValueAsString(entity))
                            .contentType(APPLICATION_JSON);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);

        }

    }

    public MockHttpServletRequestBuilder getPutRequestBuilder(String url, Object entity) {

        try {

            return put(url).content(objectMapper.writeValueAsString(entity))
                           .contentType(APPLICATION_JSON);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);

        }

    }

    public RequestPostProcessor authentication() {

        return request -> {

            request.addHeader(AUTHORIZATION, getAuthorizationHeader());

            return request;

        };

    }

    public RequestPostProcessor remoteAddr() {

        return request -> {

            request.setRemoteAddr("127.0.0.1");

            return request;

        };

    }

    public RequestPostProcessor userAgent() {

        return request -> {

            request.addHeader(USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67");

            return request;

        };

    }

    public MockMvc getMockMvc() {

        return mockMvc;

    }

    private String getAuthorizationHeader() {

        Admin admin = Admin.builder()
                           .login("admin")
                           .password("admin")
                           .build();

        try {

            MvcResult result = mockMvc.perform(getPostRequestBuilder("/api/auth", admin)).andReturn();

            return result.getResponse().getHeader(AUTHORIZATION);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}
