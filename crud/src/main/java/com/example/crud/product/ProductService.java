package com.example.crud.product;

import com.example.crud.ProductCategory.Category;
import com.example.crud.ProductCategory.CategoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Product> getListaDeProductos() {
        return this.productRepository.findAll();
    }

    public ResponseEntity<Object> newProduct(Product product) {
        Optional<Product> respuesta = productRepository.findProductByName(product.getName());
        HashMap<String, Object> datos = new HashMap<>();
        if (respuesta.isPresent()) {
            datos.put("error", true);
            datos.put("message", "Ya existe un producto con ese nombre");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.BAD_REQUEST
            );
        }

        Integer categoryId = product.getCategory().getId();
        Optional<Category> categoryOpcional = categoryRepository.findById(categoryId);
        if (categoryOpcional.isEmpty()) {
            datos.put("error", true);
            datos.put("message", "La categoría ingresada no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.NOT_FOUND
            );
        }
        product.setCategory(categoryOpcional.get());
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(
                savedProduct,
                HttpStatus.CREATED
        );
    }


    public ResponseEntity<Object> updateProduct(Long id, Product product) {
        Optional<Product> productoASerModificadoOptional = productRepository.findById(id);
        if (productoASerModificadoOptional.isEmpty()) {
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("error", true);
            datos.put("message", "El producto a modificar no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.NOT_FOUND
            );
        }
        Integer updatedCategoryId = product.getCategory().getId();
        if (!categoryRepository.existsById(updatedCategoryId)) {
            HashMap<String, Object> datos = new HashMap<>();
            datos.put("error", true);
            datos.put("message", "La categoría no existe");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.NOT_FOUND
            );
        }
        Optional<Category> newCategoryIdOptional = categoryRepository.findById(product.getCategory().getId());
        Category newCategory = newCategoryIdOptional.get();
        Product productToBeModified = productoASerModificadoOptional.get();
        productToBeModified.setId(id);
        productToBeModified.setName(product.getName());
        productToBeModified.setDate(product.getDate());
        productToBeModified.setPrice(product.getPrice());
        productToBeModified.setAntiguedad(product.getAntiguedad());
        productToBeModified.setCategory(newCategory);
        Product updatedProduct = productRepository.save(productToBeModified);
        return new ResponseEntity<>(
                updatedProduct,
                HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteProduct(Long id) {
        Optional<Product> productoASerEliminado = productRepository.findById(id);
        if (productoASerEliminado.isPresent()) {
            productRepository.delete(productoASerEliminado.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> getProductById(Long id) {
        Optional<Product> productFinded= productRepository.findById(id);
        if(productFinded.isPresent()){
            return new ResponseEntity<>(
                    productFinded.get(),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND);
        }
    }
}

