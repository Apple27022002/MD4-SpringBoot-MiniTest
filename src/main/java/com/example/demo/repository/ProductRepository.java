package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    //- tìm ra các product còn trong kho > 300
    @Query(value = "select * from product where price > 300", nativeQuery = true)
    List<Product> findAllByPriceGreaterThan300();
    //- tìm product theo tên
    List<Product> findAllByNameContaining(String name);
    //- sắp xếp giá tăng dần, giảm dần
    List<Product> findAllByOrderByPriceDesc();

}
