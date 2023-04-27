package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "amenity")
public class Amenity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "amenity")
    private String amenity;

    public Amenity(String amenity) {
        this.amenity = amenity;
    }

    public Amenity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }
}
