package com.dart.explore.repository;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Integer> {
    @Query("SELECT s FROM Station s JOIN s.color c WHERE c IN :colors")
    List<Station> findStationsByColors(@Param("colors") List<StationColor> colors);

    Station findFirstBy();

    Optional<Station> findByStationIdIs(Long stationId);
}
