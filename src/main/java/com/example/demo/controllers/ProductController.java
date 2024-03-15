package com.example.demo.controllers;

import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import com.example.demo.domain.product.RequestProduct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository  repository;
    //procurar
    @GetMapping
    public ResponseEntity getAllProduct(){
        var allProducts = repository.findAll();
        return  ResponseEntity.ok(allProducts);
    }

    //criar
    @PostMapping
     public ResponseEntity createPostProduct(@RequestBody @Valid RequestProduct data){
       Product newProduct = new Product(data);
        repository.save(newProduct);
     return ResponseEntity.ok().build();
    }

    //atualizar

    @PutMapping
    @Transactional

    public ResponseEntity updatePutProduct( @RequestBody @Valid RequestProduct data){
        Optional<Product> optionalProduct  = repository.findById(data.id());
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok(product);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){
     repository.deleteById(id);
     return ResponseEntity.noContent().build();
    }
}