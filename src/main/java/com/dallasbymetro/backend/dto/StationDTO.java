package com.dallasbymetro.backend.dto;

import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.entity.StationColor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StationDTO {
    private Long stationId;
    private String name;
    private Double latitude;
    private Double longitude;
    private Set<StationColor> color;
    private List<PointOfInterestDTO> pointsOfInterest;

    public StationDTO(Station station) {
        this.stationId = station.getStationId();
        this.name = station.getName();
        this.latitude = station.getLatitude();
        this.longitude = station.getLongitude();
        this.color = station.getColor();
        this.pointsOfInterest = station.getPointOfInterest().stream()
                .map(PointOfInterestDTO::prepareDTO)
                .collect(Collectors.toList());
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