package com.dart.explore.repository;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StationRepository extends CrudRepository<Station, Integer> {
    @Query("SELECT s FROM Station s JOIN s.color c WHERE c = :color")
    List<Station> findStationByColor(@Param("color") StationColor color);
}
