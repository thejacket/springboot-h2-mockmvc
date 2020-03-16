package com.thejacket.springbootH2Mockmvc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ORDERS")
public class Order {

    @Column(name = "id")
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_name")
    String user_name;

    @Column(name = "product_mission_name")
    String product_mission_name;

    @Column(name = "product_id")
    Integer product_id;

    @Column(name = "time")
    Date time;

    public Order (){}

    public Order(String user_name, Integer product_id, String product_mission_name, Date time){
        this.user_name = user_name;
        this.product_id = product_id;
        this.product_mission_name = product_mission_name;
        this.time = time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() { return user_name; }

    public void setUser_name(Integer user_id) {
        this.user_name = user_name;
    }

    public String getProduct_mission_name() {
        return product_mission_name;
    }

    public void setProduct_mission_name(String product_mission_name) {
        this.product_mission_name = product_mission_name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
