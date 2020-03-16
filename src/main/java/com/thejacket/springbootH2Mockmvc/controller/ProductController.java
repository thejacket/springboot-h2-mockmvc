package com.thejacket.springbootH2Mockmvc.controller;

import com.thejacket.springbootH2Mockmvc.model.Product;
import com.thejacket.springbootH2Mockmvc.model.ProductSpecificationsBuilder;
import com.thejacket.springbootH2Mockmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* REST Controller for interacting with Product entities
 *  GET method: /products[?id=] Returns products of given id or returns all products if none supplied.
 *                            Access by all authenticated users
 *              /products/report/(howMany) Special method for returning most ordered products.
 *                                         howMany parameter controls number of returned products from top.
 *  POST methods: /product {data-json} Returns SUCCESS and saves to database new product with details supplied with
 *                                     in json
 *  DELETE methods: /products Returns SUCCESS and deletes all products' records from database
 *                  /product[?id=] Returns SUCCESS and deletes product of given id
 *
 * */

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    String addProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return "SUCCESS";
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    Map<String, String> deleteProduct(@RequestParam Integer id) {
        Map<String, String> status = new HashMap<>();
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            productService.delete(product.get());
            status.put("Status", "Product deleted successfully");
        } else {
            status.put("Status", "Product not exist");
        }
        return status;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> search(@RequestParam(value = "id", required = false) Integer request_id, @RequestParam(value = "search", required = false) String search) {
        if (request_id != null) {
            List<Product> list = new ArrayList<Product>();
            Optional<Product> product = productService.findById(request_id);
            if (product.isPresent()) {
                list.add(product.get());
            }
            return list;
        }
        ProductSpecificationsBuilder builder = new ProductSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Product> spec = builder.build();
        return productService.findAll(spec);
    }

    //TODO: explain that this should be done asynchronously

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    String addAllProducts(@RequestBody List<Product> productList) {
        productService.saveAll(productList);
        return "SUCCESS";
    }

    @RequestMapping(value = "/products", method = RequestMethod.DELETE)
    String deleteAllProducts() {
        productService.deleteAll();
        return "SUCCESS";
    }

    /* Send query directly to the database thanks to @Query annotation in JpaRepository interface*/
    @RequestMapping(value = "/products/report/{howMany}", method = RequestMethod.GET)
    @ResponseBody
    List<Product> reportMostOrderedProducts(@PathVariable Integer howMany) {
        List<Product> listOfProducts = productService.getMostOrderedProducts(howMany);
        return listOfProducts;
    }

}
