package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bike_parking")
    private Boolean bikeParking;

    @Column(name = "vegetarian_food")
    private Boolean vegetarianFood;

    @Column(name = "vegan_food")
    private Boolean veganFood;

    public Amenity(Boolean bikeParking, Boolean vegetarianFood, Boolean veganFood) {
        this.bikeParking = bikeParking;
        this.vegetarianFood = vegetarianFood;
        this.veganFood = veganFood;
    }

    public Amenity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBikeParking() {
        return bikeParking;
    }

    public void setBikeParking(Boolean bikeParking) {
        this.bikeParking = bikeParking;
    }

    public Boolean getVegetarianFood() {
        return vegetarianFood;
    }

    public void setVegetarianFood(Boolean vegetarianFood) {
        this.vegetarianFood = vegetarianFood;
    }

    public Boolean getVeganFood() {
        return veganFood;
    }

    public void setVeganFood(Boolean veganFood) {
        this.veganFood = veganFood;
    }
}
