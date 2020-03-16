package com.thejacket.springbootH2Mockmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Column(name = "id")
    @Id
    Integer id;

    @Column(name = "product_mission_name")
    String product_mission_name;

    @Column(name = "product_acquisition_date")
    Date product_acquisition_date;

    @Column(name = "product_footprint")
    String product_footprint;

    @Column(name = "product_price")
    Integer product_price;

    @Column(name = "product_url")
    String product_url;

    @Column(name = "order_count")
    Integer order_count;

    public Product(){}

    public Product(int id, String product_mission_name, String product_footprint, Date product_acquisition_date, int product_price, String product_url, int order_count){
        this.id = id;
        this.product_mission_name = product_mission_name;
        this.product_footprint = product_footprint;
        this.product_acquisition_date = product_acquisition_date;
        this.product_price = product_price;
        this.product_url = product_url;
        this.order_count = order_count;
    }

    public Integer getOrderCount() {
        return order_count;
    }

    public void setOrderCount(Integer order_count) {
        this.order_count = order_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductMissionName() {
        return product_mission_name;
    }

    public void setProductMissionName(String product_mission_name) {
        this.product_mission_name = product_mission_name;
    }

    public Date getProductAcquisitionDate() {
        return product_acquisition_date;
    }

    public void setProductAcquisitionDate(Date product_acquisition_date) {
        this.product_acquisition_date = product_acquisition_date;
    }

    public String getProductFootprint() {
        return product_footprint;
    }

    public void setProductFootprint(String product_footprint) {
        this.product_footprint = product_footprint;
    }

    public Integer getProductPrice() {
        return product_price;
    }

    public void setProductPrice(Integer product_price) {
        this.product_price = product_price;
    }

    public String getProductURL() {
        return product_url;
    }

    public void setProductURL(String product_url) {
        this.product_url = product_url;
    }
}
