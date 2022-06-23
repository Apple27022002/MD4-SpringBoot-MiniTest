package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface IProductService extends IGeneralService<Product>{
    List<Product> findAllByPriceGreaterThan300();

    List<Product> findAllByNameContaining(String name);
}
