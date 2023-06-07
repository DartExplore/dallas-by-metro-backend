package com.dart.explore.dto;

import com.dart.explore.entity.Amenity;
import jakarta.validation.constraints.NotNull;

public class AmenityDTO {
    @NotNull
    private Long amenityId;
    private String amenity;

    public AmenityDTO(Long amenityId, String amenity) {
        this.amenityId = amenityId;
        this.amenity = amenity;
    }

    public Long getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Long amenityId) {
        this.amenityId = amenityId;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public static AmenityDTO prepareDTO(Amenity amenity){
        return new AmenityDTO(amenity.getAmenityId(), amenity.getAmenity());
    }
}
