package com.dart.explore.repository;

import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface StationRepository extends CrudRepository<Station, Integer> {
    @Query("SELECT s FROM Station s JOIN s.color c WHERE c = :color")
    List<Station> findByColor(@Param("color") StationColor color);

    default List<Station> getStationsByLine(final StationColor color) {
        return findByColor(color);
    }

    // TODO
    default List<PointOfInterest> getPOIsByStation(String stationName) {
        return Collections.emptyList();
    }
}
