package com.dallasbymetro.backend.service;

import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.dto.StationDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.exception.ElementNotFoundException;

import java.util.List;

/* this service will provide functions for the minimal viable product
 * using this service we can do things such as getting stations by line,
 * getting POIs at a station, etc.
 */
public interface StationService {
    List<PointOfInterestDTO> getPOIs(List<Long> amenityIdList) throws DartExploreException, ElementNotFoundException;

    List<StationDTO> getStationsByLines(List<String> lines) throws DartExploreException, ElementNotFoundException;

    List<PointOfInterestDTO> getPOIsAtStation(Long stationId, List<Amenity> amenities);

    List<Amenity> getAmenitiesById(List<Long> amenityIdList);

    List<StationDTO> getAllStationsWithPOIs();

    List<PointOfInterestDTO> getPOIsById(List<Long> poiIds) throws DartExploreException, ElementNotFoundException;

    List<StationDTO> getStationsByConnection(Long currentStation, Integer stationConnections, List<Long> amenityIdList, List<String> typesList, Integer maxWalkTime, Boolean returnEmpty) throws DartExploreException, ElementNotFoundException;
}
