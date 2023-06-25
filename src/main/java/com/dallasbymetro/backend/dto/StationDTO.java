package com.dallasbymetro.backend.dto;

import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.entity.StationColor;

import java.util.List;
import java.util.Set;

public class StationDTO {
    private Long stationId;
    private String name;
    private Set<StationColor> color;
    private List<PointOfInterestDTO> pointsOfInterest;

    public StationDTO(Station station) {
        this.stationId = station.getStationId();
        this.name = station.getName();
        this.color = station.getColor();
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StationColor> getColor() {
        return color;
    }

    public void setColor(Set<StationColor> color) {
        this.color = color;
    }

    public List<PointOfInterestDTO> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(List<PointOfInterestDTO> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public static StationDTO prepareStationDTO(Station station) {
        return new StationDTO(station);
    }
}