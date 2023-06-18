package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
    @Query("SELECT p FROM PointOfInterest p JOIN p.amenities a WHERE a IN :amenities")
    List<PointOfInterest> getPOIsByAmenities(@Param("amenities") List<Amenity> amenities);

    @Query("SELECT p FROM PointOfInterest p JOIN p.station s JOIN p.amenities a WHERE s.stationId = :stationId AND a IN :amenities")
    List<PointOfInterest> getPointOfInterestsByStationAndAmenities(@Param("stationId") Long stationId, @Param("amenities") List<Amenity> amenities);

    @Query("SELECT p FROM PointOfInterest p WHERE p.station.stationId = :stationId")
    List<PointOfInterest> getPOIsByStation(@Param("stationId") Long stationId);

    List<PointOfInterest> findByStation(Station station);
}
