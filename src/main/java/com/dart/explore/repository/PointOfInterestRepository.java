package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
    @Query("SELECT p FROM PointOfInterest p JOIN p.amenities a WHERE a IN :amenities")
    List<PointOfInterest> getPOIsByAmenities(@Param("amenities") List<Amenity> amenities);

    @Query("SELECT p FROM PointOfInterest p JOIN p.station s JOIN p.amenities a WHERE s.name = :stationName AND a IN :amenities")
    List<PointOfInterest> getPointOfInterestsByStationAndAmenities(@Param("stationName") String stationName, @Param("amenities") List<Amenity> amenities);

}
