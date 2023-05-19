package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
    // TODO
    @Query("SELECT p FROM PointOfInterest p JOIN p.amenities a WHERE a IN :amenities")
    List<PointOfInterest> findByAmenitiesIn(List<Amenity> amenities);

    default List<PointOfInterest> getPOIs(List<Amenity> amenities) {
        return findByAmenitiesIn(amenities);
    }

    // TODO
    default List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities) {
        return (List<PointOfInterest>) findAll();
    }
}
