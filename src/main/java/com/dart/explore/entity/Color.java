package com.dart.explore.entity;

import javax.persistence.*;

@Entity
public class Color {
    @Id
    @Enumerated(EnumType.STRING)
    private StationColor color;
}

enum StationColor{
    BLUE, RED, ORANGE, GREEN
}