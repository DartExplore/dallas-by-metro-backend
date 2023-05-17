package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
    // TODO
    List<PointOfInterest> getPOIs(List<Amenity> amenities);

    // TODO
    List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities);
}
