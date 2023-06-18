package com.dart.explore.dto;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PointOfInterestDTO {
    @NotNull(message="POI_ID must not be null.")
    private Long poiId;
    private String name;
    private String location;
    private Integer walkingDistance;
    private String picUrl;
    private String type;
    private List<AmenityDTO> amenities;
    @NotNull(message="STATION_ID must not be null.")
    private Long stationId;

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
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

    public Integer getWalkingDistance() {
        return walkingDistance;
    }

    public void setWalkingDistance(Integer walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AmenityDTO> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<AmenityDTO> amenities) {
        this.amenities = amenities;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public static PointOfInterestDTO prepareDTO(PointOfInterest pointOfInterest) {
        PointOfInterestDTO pointOfInterestDTO = new PointOfInterestDTO();
        pointOfInterestDTO.poiId = pointOfInterest.getPoiId();
        pointOfInterestDTO.name = pointOfInterest.getName();
        pointOfInterestDTO.location = pointOfInterest.getLocation();
        pointOfInterestDTO.type = pointOfInterest.getType();
        pointOfInterestDTO.walkingDistance = pointOfInterest.getWalkingDistance();
        pointOfInterestDTO.picUrl = pointOfInterest.getPicUrl();
        pointOfInterestDTO.stationId = pointOfInterest.getStation().getStationId();
        pointOfInterestDTO.amenities = pointOfInterest.getAmenities().stream().map(AmenityDTO::prepareAmenityDTO).collect(Collectors.toList());
        return pointOfInterestDTO;
    }

    public static PointOfInterest prepareEntity(PointOfInterestDTO pointOfInterestDTO, Station station) {
        PointOfInterest pointOfInterest = new PointOfInterest();
        pointOfInterest.setPoiId(pointOfInterestDTO.getPoiId());
        pointOfInterest.setName(pointOfInterestDTO.getName());
        pointOfInterest.setLocation(pointOfInterestDTO.getLocation());
        pointOfInterest.setType(pointOfInterestDTO.getType());
        pointOfInterest.setWalkingDistance(pointOfInterestDTO.getWalkingDistance());
        pointOfInterest.setPicUrl(pointOfInterestDTO.getPicUrl());
        pointOfInterest.setStation(station);

        pointOfInterest.setAmenities((Optional.ofNullable(pointOfInterestDTO.getAmenities()).isEmpty()) ? new ArrayList<Amenity>() :
                pointOfInterestDTO.getAmenities().stream().map(AmenityDTO::prepareAmenityEntity).collect(Collectors.toList()));
        return pointOfInterest;
    }
}
