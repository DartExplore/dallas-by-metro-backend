package com.dart.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AmenityId implements Serializable {

    @Column(name = "poi_id")
    private Long poiId;

    @Column(name = "amenity")
    private String amenity;

    public AmenityId(Long poiId, String amenity) {
        this.poiId = poiId;
        this.amenity = amenity;
    }

    public AmenityId() {

    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmenityId amenityId)) return false;

        if (!poiId.equals(amenityId.poiId)) return false;
        return amenity.equals(amenityId.amenity);
    }

    @Override
    public int hashCode() {
        int result = poiId.hashCode();
        result = 31 * result + amenity.hashCode();
        return result;
    }
}
