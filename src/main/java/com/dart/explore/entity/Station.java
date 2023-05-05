package com.dart.explore.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long poiId;
    private String name;
    private String location;
    @OneToMany
    @JoinColumn(name="station_id")
    private List<PointOfInterest> pointOfInterest;

    public Station(String name, String location, List<PointOfInterest> pointOfInterest) {
        this.name = name;
        this.location = location;
        this.pointOfInterest = pointOfInterest;
    }

    public Station() {

    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
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

    public List<PointOfInterest> getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(List<PointOfInterest> pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }
}

