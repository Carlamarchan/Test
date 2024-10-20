package com.example.crud.ProductCategory;

import com.example.crud.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "api/v1/category")
    public List<Category> getListaDeCategorias() {
        return this.categoryService.getListadeCategorias();
    }

    @GetMapping(path = "api/v1/category/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer id){
        return this.categoryService.getCategoryById(id);
    }

    @PostMapping(path = "api/v1/category")
    public ResponseEntity<Object> categoryRegister(@RequestBody Category category) {
        return this.categoryService.newCategory(category);
    }

    @PutMapping(path = "api/v1/category/{id}")
    public ResponseEntity<Object> categoryUpdate(@PathVariable Integer id, @RequestBody Category category) {
        return this.categoryService.updateCategory(id, category);
    }

    @DeleteMapping (path = "api/v1/category/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer id){
        return this.categoryService.deleteCategory(id);
    }
}


