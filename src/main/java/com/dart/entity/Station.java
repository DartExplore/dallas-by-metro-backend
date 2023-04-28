package com.dart.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "station")
    private List<PointOfInterest> pointOfInterest;

    public Station(String name, String location, List<PointOfInterest> pointOfInterest) {
        this.name = name;
        this.location = location;
        this.pointOfInterest = pointOfInterest;
    }

    public Station() {

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

    public List<PointOfInterest> getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(List<PointOfInterest> pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }
}

