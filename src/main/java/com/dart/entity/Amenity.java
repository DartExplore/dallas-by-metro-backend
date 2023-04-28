// Amenity.java
package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "amenity")
public class Amenity {

    @EmbeddedId
    private AmenityId id;

    @ManyToOne
    @MapsId("poiId")
    @JoinColumn(name = "poi_id")
    private PointOfInterest pointOfInterest;

    @Column(name = "amenity")
    private String amenity;

    public Amenity(PointOfInterest pointOfInterest, String amenity) {
        this.pointOfInterest = pointOfInterest;
        this.amenity = amenity;
    }

    public Amenity() {

    }

    public AmenityId getId() {
        return id;
    }

    public void setId(AmenityId id) {
        this.id = id;
    }

    public PointOfInterest getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(PointOfInterest pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }
}
