package com.dart.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "edges")
public class Edge {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "stations")
    private String stations;

    @Column(name = "points_of_interest")
    private String pointsOfInterest;

    @Column(name = "colors")
    private String colors;

    @ManyToMany(mappedBy = "edges")
    private List<Station> stationsList;

    public Edge(String stations, String pointsOfInterest, String colors, List<Station> stationsList) {
        this.stations = stations;
        this.pointsOfInterest = pointsOfInterest;
        this.colors = colors;
        this.stationsList = stationsList;
    }

    public Edge() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public String getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(String pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public List<Station> getStationsList() {
        return stationsList;
    }

    public void setStationsList(List<Station> stationsList) {
        this.stationsList = stationsList;
    }

}

