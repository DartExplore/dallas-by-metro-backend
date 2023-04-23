package com.dart.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToOne(mappedBy = "station")
    private PointOfInterest pointOfInterest;

    @ManyToMany
    @JoinTable(
            name = "station_amenities",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;

    @ManyToMany
    @JoinTable(
            name = "station_edges",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "edge_id")
    )
    private List<Edge> edges;

    public Station(String name, String location, PointOfInterest pointOfInterest, List<Amenity> amenities, List<Edge> edges) {
        this.name = name;
        this.location = location;
        this.pointOfInterest = pointOfInterest;
        this.amenities = amenities;
        this.edges = edges;
    }

    public Station() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PointOfInterest getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(PointOfInterest pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}

