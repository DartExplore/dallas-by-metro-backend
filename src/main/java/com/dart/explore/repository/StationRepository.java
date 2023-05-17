package com.dart.explore.repository;

import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepository extends CrudRepository<Station, Integer> {
    // TODO
    List<Station> getStationsByLine(StationColor color);

    // TODO
    List<PointOfInterest> getPOIsByStation(String stationName);
}
