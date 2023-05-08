package com.dart.explore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.Color;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;

@Service(value="stationService")
public class StationServiceImpl implements StationService {

	@Override
	public List<PointOfInterest> getPOIs(List<Amenity> amenities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Station> getStationsByLine(Color color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointOfInterest> getPOIsByStation(String stationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities) {
		// TODO Auto-generated method stub
		return null;
	}

}
