package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "points_of_interest")
public class PointOfInterest {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "walking_distance")
    private Integer walkingDistance;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "type")
    private String type;

    @OneToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public PointOfInterest(String name, String location, Integer walkingDistance, String picUrl, String type, Station station) {
        this.name = name;
        this.location = location;
        this.walkingDistance = walkingDistance;
        this.picUrl = picUrl;
        this.type = type;
        this.station = station;
    }

    public PointOfInterest() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(Integer walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}

