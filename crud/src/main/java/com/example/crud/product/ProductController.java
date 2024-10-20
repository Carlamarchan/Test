package com.example.crud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path="api/v1/products")
    public List<Product> getListaDeProductos() {
        return this.productService.getListaDeProductos();
    }

    @GetMapping(path = "api/v1/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        return this.productService.getProductById(id);
    }

    @PostMapping (path="api/v1/products")
    public ResponseEntity <Object>registrarProducto(@RequestBody Product product){
        return this.productService.newProduct(product);
    }

    @PutMapping (path="api/v1/products/{id}")
    public ResponseEntity <Object>actualizarProducto(@PathVariable Long id, @RequestBody Product product){
        return this.productService.updateProduct(id,product);
    }

    @DeleteMapping (path="api/v1/products/{id}")
    public ResponseEntity <Object>deleteProduct(@PathVariable Long id){
       return productService.deleteProduct(id);
    }

}
