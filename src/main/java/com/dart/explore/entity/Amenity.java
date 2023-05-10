package com.dart.explore.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;
    @ManyToMany
    private Set<PointOfInterest> pointsOfInterest;
    private String amenity;

    public Amenity(Set<PointOfInterest> pointOfInterest, String amenity) {
        this.pointsOfInterest = pointOfInterest;
        this.amenity = amenity;
    }

    public Amenity() {

    }

    public Long getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Long amenityId) {
        this.amenityId = amenityId;
    }

    public Set<PointOfInterest> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(Set<PointOfInterest> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }
}
