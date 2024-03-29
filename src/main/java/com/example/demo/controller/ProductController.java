package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProductService iProductService;

    @GetMapping//Tìm
    public ResponseEntity<Iterable<Product>> findAll() {
        return new ResponseEntity<>(iProductService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        Optional<Product> productOptional = iProductService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

    @PostMapping("")//Add-Thêm-RequestBody để lấy product trực tiếp từ web về để dùng
    public ResponseEntity add(@Valid @RequestBody Product product) {
        iProductService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{id}")//EDIT-Sửa
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productOptional = iProductService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(productOptional.get().getId());
        return new ResponseEntity<>(   iProductService.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//Delete-Xóa
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Optional<Product> productOptional = iProductService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iProductService.remove(id);
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }


    @GetMapping("/greater-than-300")
    public ResponseEntity findAllByPriceGreaterThan300() {
        return new ResponseEntity( iProductService.findAllByPriceGreaterThan300(), HttpStatus.OK);
    }
    @GetMapping("/find-by-name")
    public ResponseEntity findAllByNameContaining(@RequestParam String name) {
        return new ResponseEntity( iProductService.findAllByNameContaining(name), HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
