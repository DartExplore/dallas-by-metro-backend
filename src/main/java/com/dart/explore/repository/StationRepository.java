package com.dart.explore.repository;

import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collections;
import java.util.List;

public interface StationRepository extends CrudRepository<Station, Integer> {
    // TODO
    List<Station> getStationsByColor(StationColor color);

    default List<Station> getStationsByLine(final StationColor color) {
        return getStationsByColor(color);
    }

    // TODO
    default List<PointOfInterest> getPOIsByStation(String stationName) {
        return Collections.emptyList();
    }
}
