package com.dart.explore.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StationConnectionId implements Serializable {

    @Column(name = "station1_id")
    private Long station1Id;

    @Column(name = "station2_id")
    private Long station2Id;

    public StationConnectionId(Long station1Id, Long station2Id) {
        this.station1Id = station1Id;
        this.station2Id = station2Id;
    }

    public StationConnectionId() {

    }

    public Long getStation1Id() {
        return station1Id;
    }

    public void setStation1Id(Long station1Id) {
        this.station1Id = station1Id;
    }

    public Long getStation2Id() {
        return station2Id;
    }

    public void setStation2Id(Long station2Id) {
        this.station2Id = station2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationConnectionId that)) return false;

        if (!station1Id.equals(that.station1Id)) return false;
        return station2Id.equals(that.station2Id);
    }

    @Override
    public int hashCode() {
        int result = station1Id.hashCode();
        result = 31 * result + station2Id.hashCode();
        return result;
    }
}
