package com.dallasbymetro.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long stationId;
    private String name;
    private Double latitude;
    private Double longitude;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JsonIgnore
    @JoinColumn(name = "station_id")
    private List<PointOfInterest> pointOfInterest;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(
            name = "station_connection",
            joinColumns = @JoinColumn(name = "station1_id"),
            inverseJoinColumns = @JoinColumn(name = "station2_id")
    )
    private final Set<Station> connectedStations = new HashSet<>();
    @ElementCollection(targetClass = StationColor.class)
    @CollectionTable(name = "station_color", joinColumns = @JoinColumn(name = "station_id"))
    @Enumerated(EnumType.STRING)
    private Set<StationColor> color = new HashSet<>();

    public Station() {

    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<PointOfInterest> getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(List<PointOfInterest> pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<StationColor> getColor() {
        return color;
    }

    public void setColor(Set<StationColor> color) {
        this.color = color;
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

    public Set<Station> getConnectedStations() {
        return connectedStations;
    }
}
