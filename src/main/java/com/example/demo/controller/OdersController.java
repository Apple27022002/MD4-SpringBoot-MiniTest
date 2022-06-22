package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.service.IOrdersService;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OdersController {
    @Autowired
    IOrdersService iOrdersService;


    @GetMapping//Tìm
    public ResponseEntity<Iterable<Orders>> findAll() {
        return new ResponseEntity<>(iOrdersService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findProductById(@PathVariable Long id) {
        Optional<Orders> ordersOptional = iOrdersService.findById(id);
        if (!ordersOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersOptional.get(), HttpStatus.OK);
    }


    @PostMapping("")//Add-Thêm-RequestBody để lấy product trực tiếp từ web về để dùng
    public ResponseEntity add(@RequestBody Orders orders) {
        iOrdersService.save(orders);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{id}")//EDIT-Sửa
    public ResponseEntity<Orders> update(@PathVariable Long id, @RequestBody Orders orders) {
        Optional<Orders> ordersOptional = iOrdersService.findById(id);
        if (!ordersOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orders.setId(ordersOptional.get().getId());
        iOrdersService.save(orders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//Delete-Xóa
    public ResponseEntity<Orders> delete(@PathVariable Long id) {
        Optional<Orders> ordersOptional = iOrdersService.findById(id);
        if (!ordersOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iOrdersService.remove(id);
        return new ResponseEntity<>(ordersOptional.get(), HttpStatus.OK);
    }
}


