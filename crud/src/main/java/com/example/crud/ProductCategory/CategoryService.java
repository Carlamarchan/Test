package com.example.crud.ProductCategory;

import com.example.crud.product.Product;
import com.example.crud.product.ProductRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getListadeCategorias() {
        return this.categoryRepository.findAll();
    }

    public ResponseEntity<Object> newCategory(Category category) {
        Optional<Category> respuesta = categoryRepository.findCategoryByName(category.getName());

        if (respuesta.isPresent() && category.getId() == null) {
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("error", true);
            datos.put("message", "Ya existe una categoría con ese nombre");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        categoryRepository.save(category);
        return new ResponseEntity<>(
                category,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateCategory(Integer id, Category category) {
        Optional<Category> categoriaASerModificadoOptional = categoryRepository.findById(id);
        if (categoriaASerModificadoOptional.isPresent()) {
            Category categoriaASerModificada = categoriaASerModificadoOptional.get();
            categoriaASerModificada.setId(id);
            categoriaASerModificada.setName(category.getName());
            categoryRepository.save(categoriaASerModificada);
            return new ResponseEntity<>(
                    categoriaASerModificada,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> deleteCategory(Integer id) {
        Optional<Category> categoriaASerModificadoOptional = categoryRepository.findById(id);
        if (categoriaASerModificadoOptional.isPresent()) {
            categoryRepository.delete(categoriaASerModificadoOptional.get());
            return new ResponseEntity<>(
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }
    }

    public  ResponseEntity<Object> getCategoryById(Integer id) {
        Optional<Category> categoryfinded= categoryfinded= categoryRepository.findById(id);
        if(categoryfinded.isPresent()){
            return new ResponseEntity<>(
                    categoryfinded.get(),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }
    }
}

