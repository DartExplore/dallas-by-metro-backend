package com.dart.explore.service;

import java.util.List;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.exception.DartExploreException;

/* this service will provide functions for the minimal viable product
 * using this service we can do things such as getting stations by line,
 * getting POIs at a station, etc.
 */
public interface StationService {
    List<PointOfInterestDTO> getPOIs(List<Amenity> amenities);

    List<StationDTO> getStationsByLines(List<String> lines) throws DartExploreException;

    List<PointOfInterestDTO> getPOIsAtStation(Long stationId, List<Amenity> amenities);

    List<Amenity> getAmenitiesById(List<Long> amenityIdList);

    List<StationDTO> getAllStationsWithPOIs();

    List<PointOfInterestDTO> getPOIsById(List<Long> poiIds) throws DartExploreException;
}
