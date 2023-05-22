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

    // TODO
    default List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities) {
        return (List<PointOfInterest>) findAll();
    }
}
