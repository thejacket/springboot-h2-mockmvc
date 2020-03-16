package com.thejacket.springbootH2Mockmvc.service;

import com.thejacket.springbootH2Mockmvc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderService extends JpaRepository<Order, Integer> {
    @Query(
            value= "SELECT * FROM ORDERS WHERE USER_NAME = ?1 ORDER BY TIME DESC",
            nativeQuery = true)
    List<Order> getUserOrderHistoryByName(String user_name);
}
