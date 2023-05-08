package com.dart.explore.service;

import java.util.List;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.Color;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;

/* this service will provide functions for the minimal viable product
 * using this service we can do things such as gettings stations by line,
 * getting POIs at a station, etc.
 */
public interface StationService {
	List<PointOfInterest> getPOIs(List<Amenity> amenities);
	List<Station> getStationsByLine(Color color);
	List<PointOfInterest> getPOIsByStation(String stationName);
	List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities);
}
