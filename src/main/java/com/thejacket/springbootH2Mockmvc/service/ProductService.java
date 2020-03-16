package com.thejacket.springbootH2Mockmvc.service;

import com.thejacket.springbootH2Mockmvc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductService extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query(
            value= "SELECT TOP ?1 * FROM PRODUCT ORDER BY ORDER_COUNT DESC",
            nativeQuery = true)
    List<Product> getMostOrderedProducts(Integer howMany);
}
