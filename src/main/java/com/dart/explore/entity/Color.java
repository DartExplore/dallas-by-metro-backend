package com.dart.explore.entity;

import javax.persistence.*;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Color color1 = (Color) o;
		return this.color.ordinal() == color1.getColor().ordinal();
	}

	@Override
	public int hashCode() {
		return color.ordinal();
	}
}