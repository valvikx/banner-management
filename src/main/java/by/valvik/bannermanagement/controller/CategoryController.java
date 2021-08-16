package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.domain.Category;
import by.valvik.bannermanagement.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryController extends BaseController<Category, GenericService<Category>> {

    public CategoryController(GenericService<Category> categoryService) {

        super(categoryService);

    }

}
