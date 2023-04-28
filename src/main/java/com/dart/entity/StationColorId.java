package com.dart.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StationColorId implements Serializable {

    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "color")
    private String color;

    public StationColorId(Long stationId, String color) {
        this.stationId = stationId;
        this.color = color;
    }

    public StationColorId() {

    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationColorId that)) return false;

        if (!stationId.equals(that.stationId)) return false;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        int result = stationId.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
