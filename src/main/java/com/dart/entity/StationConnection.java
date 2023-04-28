package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "station_connection")
public class StationConnection {

    @EmbeddedId
    private StationConnectionId id;

    @ManyToOne
    @MapsId("station1Id")
    @JoinColumn(name = "station1_id")
    private Station station1;

    @ManyToOne
    @MapsId("station2Id")
    @JoinColumn(name = "station2_id")
    private Station station2;

    public StationConnection(Station station1, Station station2) {
        this.station1 = station1;
        this.station2 = station2;
    }

    public StationConnection() {

    }

    public StationConnectionId getId() {
        return id;
    }

    public void setId(StationConnectionId id) {
        this.id = id;
    }

    public Station getStation1() {
        return station1;
    }

    public void setStation1(Station station1) {
        this.station1 = station1;
    }

    public Station getStation2() {
        return station2;
    }

    public void setStation2(Station station2) {
        this.station2 = station2;
    }
}
