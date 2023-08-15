package com.dallasbymetro.backend.utility;

import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.entity.StationColor;

import java.util.Set;

public class StationNode {
    public Station station;
    public int level;
    public int transferCount;
    public Set<StationColor> colors;

    public StationNode(Station station, int level, int transferCount, Set<StationColor> colors) {
        this.station = station;
        this.level = level;
        this.transferCount = transferCount;
        this.colors = colors;
    }
}
