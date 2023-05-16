package com.dart.explore.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stationId;
    private String name;
    private Float latitude;

    private Float longitude;
    @OneToMany
    @JoinColumn(name = "station_id")
    private List<PointOfInterest> pointOfInterest;
    @OneToMany
    @JoinTable(name = "station_connection")
    @MapKeyJoinColumn(name = "station2_id")
    private Map<Station, Color> stationConnections = new HashMap<>();

    public Station(String name, Float latitude, Float longitude, List<PointOfInterest> pointOfInterest) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pointOfInterest = pointOfInterest;
    }

    public Station() {

    }

    public Integer getPoiId() {
        return stationId;
    }

    public void setPoiId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public List<PointOfInterest> getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(List<PointOfInterest> pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    @Override
    public int hashCode() {
        return this.stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationId, station.stationId);
    }
}

