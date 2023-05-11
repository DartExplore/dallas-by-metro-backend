package com.dart.explore.entity;

import javax.persistence.*;

@Entity
@Table(name = "station_connection")
public class StationConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer station1_id;

    private Integer station2_id;

    private String color;

    public StationConnection(Integer station1_id, Integer station2_id, String color) {
        this.station1_id = station1_id;
        this.station2_id = station2_id;
        this.color = color;
    }

    public StationConnection() {

    }

    public Integer getStation1_id() {
        return station1_id;
    }

    public void setStation1_id(Integer station1_id) {
        this.station1_id = station1_id;
    }

    public Integer getStation2_id() {
        return station2_id;
    }

    public void setStation2_id(Integer station2_id) {
        this.station2_id = station2_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
