package com.thejacket.springbootH2Mockmvc.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "MISSION")
public class Mission {

    @Column(name = "id")
    @Id
    Integer id;

    @Column(name = "mission_name")
    String mission_name;

    @Column(name = "mission_imagery_type")
    String mission_imagery_type;

    @Column(name = "mission_start_date")
    Date mission_start_date;

    @Column(name = "mission_finish_date")
    Date mission_finish_date;

    public Mission(){}

    public Mission(Integer id, String mission_name, String mission_imagery_type, Date mission_start_date, Date mission_finish_date){
        this.id = id;
        this.mission_name = mission_name;
        this.mission_imagery_type = mission_imagery_type;
        this.mission_start_date = mission_start_date;
        this.mission_finish_date = mission_finish_date;
    }

    public String getMissionImageryType() {
        return mission_imagery_type;
    }

    public void setMissionImageryType(String mission_imagery_type) {
        this.mission_imagery_type = mission_imagery_type;
    }

    public Date getMissionStartDate() {
        return mission_start_date;
    }

    public void setMissionStartDate(Date mission_start_date) {
        this.mission_start_date = mission_start_date;
    }

    public Date getMissionFinishDate() {
        return mission_finish_date;
    }

    public void setMissionFinishDate(Date mission_finish_date) {
        this.mission_finish_date = mission_finish_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMissionName() {
        return mission_name;
    }

    public void setMissionName(String mission_name) {
        this.mission_name = mission_name;
    }
}
