package com.dart.explore.dto;

import com.dart.explore.entity.StationColor;
import com.dart.explore.entity.Station;

import java.util.HashSet;
import java.util.Set;

public class StationDTO {
    private Long stationId;
    private String name;
    private Set<StationColor> color = new HashSet<>();

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

    public static StationDTO prepareStationDTO(Station station) {
        StationDTO stationDTO = new StationDTO();
        stationDTO.stationId = station.getStationId();
        stationDTO.name = station.getName();
        stationDTO.color = station.getColor();
        return stationDTO;
    }
}
