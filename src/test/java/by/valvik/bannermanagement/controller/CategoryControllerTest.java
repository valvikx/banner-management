package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.BaseIntegrationTest;
import by.valvik.bannermanagement.domain.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends BaseIntegrationTest {

    @Autowired
    public CategoryControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {

        super(mockMvc, objectMapper);

    }

    @Test
    void getOneExpectStatusOk() throws Exception {

        getMockMvc().perform(get("/api/categories/1").with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Humor"))
                    .andExpect(jsonPath("$.reqName").value("humor"));

    }

    @Test
    void getOneWhenCategoryDoesNotExist() throws Exception {

        getMockMvc().perform(get("/categories/100").with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: 100"))
                    .andExpect(jsonPath("$.message").value("Category not found"));

    }

    @Test
    void getOneWhenIdIsLessThanOne() throws Exception {

        getMockMvc().perform(get("/api/categories/0").with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: 0"))
                    .andExpect(jsonPath("$.message").value("ID is invalid"));

    }

    @Test
    void getOneWhenIdIsInvalid() throws Exception {

        getMockMvc().perform(get("/api/categories/abc").with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: abc"))
                    .andExpect(jsonPath("$.message").value("ID is invalid"));


    }

    @Test
    void getOneWithoutAuthenticate() throws Exception {

        getMockMvc().perform(get("/api/categories/1"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.message").value("Authentication is required to access this resource"));

    }

    @Test
    void getAll() throws Exception {

        getMockMvc().perform(get("/api/categories").with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)));

    }

    @Test
    void create() throws Exception {

        Category cardGamesCategory = Category.builder()
                                             .name("Card games")
                                             .reqName("card-games")
                                             .build();

        getMockMvc().perform(getPostRequestBuilder("/api/categories", cardGamesCategory).with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("Card games"))
                    .andExpect(jsonPath("$.reqName").value("card-games"))
                    .andExpect(jsonPath("$.isDeleted").value(false));

    }

    @Test
    void createWhenCategoryAlreadyExists() throws Exception {

        Category duplicateHumorCategory = Category.builder().name("Humor")
                                                            .reqName("humor")
                                                            .build();

        getMockMvc().perform(getPostRequestBuilder("/api/categories", duplicateHumorCategory).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").value("Category Name already exists"))
                    .andExpect(jsonPath("$.reqName").value("Request ID already exists"));
    }

    @Test
    void createWhenCategoryFieldsAreBlank() throws Exception {

        Category blankFieldsCategory = Category.builder()
                                               .name("")
                                               .reqName("")
                                               .build();

        getMockMvc().perform(getPostRequestBuilder("/api/categories", blankFieldsCategory).with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.name").value("Name is mandatory"))
                    .andExpect(jsonPath("$.reqName").value("Request ID is mandatory"));

    }

    @Test
    void update() throws Exception {

        Category editedCategory = Category.builder()
                                          .id(3)
                                          .name("Super arcade games")
                                          .reqName("arcade-games")
                                          .build();

        getMockMvc().perform(getPutRequestBuilder("/api/categories/3", editedCategory).with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(3))
                    .andExpect(jsonPath("$.name").value("Super arcade games"))
                    .andExpect(jsonPath("$.reqName").value("arcade-games"));

    }

    @Test
    void remove() throws Exception {

        getMockMvc().perform(delete("/api/categories/3").with(authentication()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(3))
                    .andExpect(jsonPath("$.name").value("Arcade games"))
                    .andExpect(jsonPath("$.reqName").value("arcade-games"));

    }

    @Test
    void removeWhenCategoryIsAssociatedWithBanners() throws Exception {

        getMockMvc().perform(delete("/api/categories/1").with(authentication()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.reason").value("Value: Humor"))
                    .andExpect(jsonPath("$.message").value("Category is associated with banner IDs: [1, 2, 3]"));


    }

}