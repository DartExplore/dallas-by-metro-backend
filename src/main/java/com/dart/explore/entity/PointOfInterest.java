package com.dart.explore.entity;

import javax.persistence.*;
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
    @ManyToMany
    @JoinTable(
            name = "poi_amenity",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    public PointOfInterest() {

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
