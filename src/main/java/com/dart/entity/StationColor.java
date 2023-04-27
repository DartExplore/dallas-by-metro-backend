package com.dart.entity;

import javax.persistence.*;

@Entity
@Table(name = "station_color")
public class StationColor {

    @Id
    @ManyToOne
    @JoinColumn(name = "station1_id", referencedColumnName = "id", nullable = false)
    private Station station1;

    @Id
    @ManyToOne
    @JoinColumn(name = "station2_id", referencedColumnName = "id", nullable = false)
    private Station station2;

    @Column(name = "color", nullable = false)
    private String color;

    public StationColor() {
    }
}
