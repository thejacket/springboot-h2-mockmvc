package com.thejacket.springbootH2Mockmvc.controller;

import com.thejacket.springbootH2Mockmvc.model.Order;
import com.thejacket.springbootH2Mockmvc.model.Product;
import com.thejacket.springbootH2Mockmvc.service.OrderService;
import com.thejacket.springbootH2Mockmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/* REST Controller for interacting with Order entities
 *  GET method: /orders[?id=] Returns order of given id or returns all orders if none supplied.
 *                            Access by manager only
 *              /orders/user_report/{user_name} Special method for returning order history of given username
 *  POST methods: /order/(product_id) Returns SUCCESS and saves to database new order of product with given id
 *  DELETE methods: /orders Returns SUCCESS and deletes all orders' records from database
 *                  /order[?id=] Returns SUCCESS and deletes order of given id
 *
 * */

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/order/{productId}", method = RequestMethod.POST)
    Map<String, String> addOrder(@PathVariable Integer productId, @AuthenticationPrincipal User user){
        Map<String, String> status = new HashMap<>(); //ad hoc JSON creation
        Optional<Product> foundProduct = productService.findById(productId);
        if(foundProduct.isPresent()) {
            Product confirmedProduct = foundProduct.get();
            confirmedProduct.setOrderCount(confirmedProduct.getOrderCount() + 1);
            productService.save(confirmedProduct);
            Date date = new Date();
            Order order = new Order(user.getUsername(), confirmedProduct.getId(), confirmedProduct.getProductMissionName(), new Timestamp(date.getTime()));
            orderService.save(order);
            status.put("Status", "SUCCESS");
        }
        else {
            status.put("Status", "Produce of given id does not exist");
        }
        return status;
    }

    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    Map<String, String> deleteOrder(@RequestParam Integer id){
        Map<String, String> status = new HashMap<>();
        Optional<Order> order = orderService.findById(id);
        if(order.isPresent()) {
            orderService.delete(order.get());
            status.put("Status", "Order deleted successfully");
        }
        else {
            status.put("Status", "Order not exist");
        }
        return status;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getOrders(@RequestParam(value="id", required = false) Integer request_id) {
        if (request_id != null) {
            List<Order> list = new ArrayList<Order>();
            Optional<Order> order = orderService.findById(request_id);
            if (order.isPresent()) {
                list.add(order.get());
            }
            return list;
        }
        return orderService.findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    String addAllOrders(@RequestBody List<Order> ordersList){
        orderService.saveAll(ordersList);
        return "SUCCESS";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.DELETE)
    String deleteAllOrders(){
        orderService.deleteAll();
        return "SUCCESS";
    }

    /* Send query directly to the database thanks to @Query annotation in JpaRepository interface*/
    @ResponseBody
    @RequestMapping(value = "/orders/user_report/{user_name}", method = RequestMethod.GET)
    ResponseEntity<?> reportMostOrderedProducts(@PathVariable String user_name){
        List<Order> listOfProducts = orderService.getUserOrderHistoryByName(user_name);
        if(listOfProducts.isEmpty()) {
            return new ResponseEntity<>(String.format("No records found for %s", user_name), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(listOfProducts, HttpStatus.OK);
        }
    }
}