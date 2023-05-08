package com.dart.explore.entity;

import javax.persistence.*;

@Entity
public class Color {
    @Id
    @Enumerated(EnumType.STRING)
    private StationColor color;

	public StationColor getColor() {
		return color;
	}

	public void setColor(StationColor color) {
		this.color = color;
	}
}