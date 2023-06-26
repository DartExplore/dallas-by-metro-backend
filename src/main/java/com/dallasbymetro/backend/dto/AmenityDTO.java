package com.dallasbymetro.backend.dto;

import com.dallasbymetro.backend.entity.Amenity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AmenityDTO {
    @NotNull(message = "AMENITY_ID must not be null.")
    private Long amenityId;
    @NotNull(message = "AMENITY name must not be null.")
    @Pattern(regexp = "^[A-Z][A-Z_]*(?<!_)$", message = "AMENITY name is in invalid format. Must be uppercase snake case.")
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
