package com.dart.explore.entity;

import javax.persistence.*;

@Entity
@Table(name = "station_color")
public class StationColor {

    @EmbeddedId
    private StationColorId id;

    @ManyToOne
    @MapsId("stationId")
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "color", nullable = false)
    private String color;

    public StationColor(Station station, String color) {
        this.station = station;
        this.color = color;
    }

    public StationColor() {

    }

    public StationColorId getId() {
        return id;
    }

    public void setId(StationColorId id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
