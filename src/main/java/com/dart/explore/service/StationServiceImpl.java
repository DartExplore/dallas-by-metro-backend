package com.dart.explore.service;

import java.util.List;

import com.dart.explore.entity.StationColor;
import com.dart.explore.repository.PointOfInterestRepository;
import com.dart.explore.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;


@Service(value = "stationService")
public class StationServiceImpl implements StationService {
    @Autowired
    PointOfInterestRepository pointOfInterestRepository;
    @Autowired
    StationRepository stationRepository;

    @Override
    public List<PointOfInterest> getPOIs(List<Amenity> amenities) {
        return pointOfInterestRepository.getPOIsByAmenities(amenities);
    }

    @Override
    public List<Station> getStationsByLine(StationColor color) {
        return stationRepository.getStationsByLine(color);
    }

    @Override
    public List<PointOfInterest> getPOIsByStation(String stationName) {
        // TODO Auto-generated method stub
        return stationRepository.getPOIsByStation(stationName);
    }

    @Override
    public List<PointOfInterest> getPOIsAtStation(String stationName, List<Amenity> amenities) {
        // TODO Auto-generated method stub
        return pointOfInterestRepository.getPointOfInterestsByStationAndAmenities(stationName, amenities);
    }

}
