package com.dart.explore.dto;

import com.dart.explore.entity.StationColor;
import com.dart.explore.entity.Station;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StationDTO {
    @NotNull(message = "{property.idNotNull}")
    private Integer stationId;
    private String name;
    private Set<StationColor> color = new HashSet<>();

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

    public Set<StationColor> getColor() {
        return color;
    }

    public static StationDTO prepareDTO(Station station){
        StationDTO stationDTO = new StationDTO();
        stationDTO.stationId = station.getStationId();
        stationDTO.name = station.getName();
        stationDTO.color = station.getColor();
        return stationDTO;
    }
}
