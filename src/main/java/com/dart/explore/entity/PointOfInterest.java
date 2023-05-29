package com.dart.explore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "points_of_interest")
public class PointOfInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long poiId;
    private String name;
    private String location;
    private Integer walkingDistance;
    private String picUrl;
    private String type;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "poi_amenity",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    public PointOfInterest() {

    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
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

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointOfInterest that = (PointOfInterest) o;
        return Objects.equals(poiId, that.poiId) && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(walkingDistance, that.walkingDistance) && Objects.equals(picUrl, that.picUrl) && Objects.equals(type, that.type) && Objects.equals(station, that.station) && Objects.equals(amenities, that.amenities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poiId);
    }
}
