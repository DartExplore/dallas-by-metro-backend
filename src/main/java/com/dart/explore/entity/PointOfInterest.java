package com.dart.explore.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "points_of_interest")
public class PointOfInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poiId;
    private String name;
    private String location;
    private Integer walkingDistance;
    private String picUrl;
    private String type;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
    @ManyToMany
    @JoinTable(
            name = "poi_amenity",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    public PointOfInterest(String name, String location, Integer walkingDistance, String picUrl, String type, Station station, List<Amenity> amenities) {
        this.name = name;
        this.location = location;
        this.walkingDistance = walkingDistance;
        this.picUrl = picUrl;
        this.type = type;
        this.station = station;
        this.amenities = amenities;
    }

    public PointOfInterest() {

    }

    public Long getStationId() {
        return poiId;
    }

    public void setStationId(Long stationId) {
        this.poiId = stationId;
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

    public List<Amenity> getAmenities() {
        return amenities;
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
