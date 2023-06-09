package com.dart.explore.dto;

import com.dart.explore.entity.Amenity;

public class AmenityDTO {
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

    public static AmenityDTO prepareAmenityDTO(Amenity amenity) {
        return new AmenityDTO(amenity.getAmenityId(), amenity.getAmenity());
    }

    public static Amenity prepareAmenityEntity(AmenityDTO amenityDTO) {
        Amenity amenity = new Amenity();
        amenity.setAmenityId(amenityDTO.getAmenityId());
        amenity.setAmenity(amenityDTO.getAmenity());
        return amenity;
    }
}
