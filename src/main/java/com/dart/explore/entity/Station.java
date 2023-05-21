package com.dart.explore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer stationId;
    private String name;
    private Double latitude;
    private Double longitude;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "station_id")
    private List<PointOfInterest> pointOfInterest;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "station_connection",
            joinColumns = @JoinColumn(name = "station1_id"),
            inverseJoinColumns = @JoinColumn(name = "station2_id")
    )
    private Set<Station> connectedStations = new HashSet<>();
    @ElementCollection(targetClass = StationColor.class)
    @CollectionTable(name = "station_color", joinColumns = @JoinColumn(name = "station_id"))
    @Enumerated(EnumType.STRING)
    private Set<StationColor> color = new HashSet<>();

    public Station(String name, Double latitude, Double longitude, List<PointOfInterest> pointOfInterest) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pointOfInterest = pointOfInterest;
    }

    public Station() {

    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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
        return Objects.hash(stationId, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(stationId, station.stationId);
    }

    public Collection<StationColor> getColor() {
        return color;
    }

    public Set<Station> getConnectedStations() {
        return connectedStations;
    }
}
