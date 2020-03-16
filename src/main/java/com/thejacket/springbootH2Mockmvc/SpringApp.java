package com.thejacket.springbootH2Mockmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO: private access to fields in model classes
//TODO: in future? storing reports as JPA objects for better memory management

//TODO: explain why enum is bad and reference tables are better

//TODO: explain serialization/deserialization in productFootprint for coordinates (sql vs nosql)
//TODO: explain since image area is a rectangle, we can simply store 2 coordinates (diagonal) in serialized way instead of 4

//TODO: tests
//TODO: check whether ordering properly increment values
//TODO: check if adding orders works and can retrieve it with GET
//TODO: check

// logout doesnt work in HTTP basic auth, only way is to go to resource that invalidates proper authentication data
// with incorrect one to return 401 Unauthorized

@SpringBootApplication
public class SpringApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringApp.class, args);
	}
}
