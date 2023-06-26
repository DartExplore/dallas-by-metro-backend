package com.dallasbymetro.backend.repository;

import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
    @Query("SELECT p FROM PointOfInterest p JOIN p.amenities a WHERE a IN :amenities GROUP BY p HAVING COUNT(DISTINCT a) >= :amenityCount")
    List<PointOfInterest> getPOIsByAmenities(@Param("amenities") List<Amenity> amenities, @Param("amenityCount") Integer amenityCount);


    @Query("SELECT p FROM PointOfInterest p JOIN p.station s JOIN p.amenities a WHERE s.stationId = :stationId AND a IN :amenities")
    List<PointOfInterest> getPointOfInterestsByStationAndAmenities(@Param("stationId") Long stationId, @Param("amenities") List<Amenity> amenities);

    List<PointOfInterest> findByStation(Station station);

    @Query("SELECT DISTINCT p.type FROM PointOfInterest p")
    List<String> getAllTypes();
}
