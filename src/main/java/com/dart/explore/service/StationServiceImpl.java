package com.dart.explore.service;

import java.util.List;
import java.util.stream.Collectors;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.StationColor;
import com.dart.explore.repository.AmenityRepository;
import com.dart.explore.repository.PointOfInterestRepository;
import com.dart.explore.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dart.explore.entity.Amenity;


@Service(value = "stationService")
public class StationServiceImpl implements StationService {
    @Autowired
    PointOfInterestRepository pointOfInterestRepository;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    AmenityRepository amenityRepository;

    @Override
    public List<PointOfInterestDTO> getPOIs(List<Amenity> amenities) {
        return pointOfInterestRepository.getPOIsByAmenities(amenities).stream().map(PointOfInterestDTO::preparePOIDTO).collect(Collectors.toList());
    }

    @Override
    public List<StationDTO> getStationsByLine(StationColor color) {
        return stationRepository.findStationByColor(color).stream().map(StationDTO::prepareStationDTO).collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterestDTO> getPOIsByStation(String stationName) {
        return pointOfInterestRepository.getPOIsByStation(stationName).stream().map(PointOfInterestDTO::preparePOIDTO).collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterestDTO> getPOIsAtStation(String stationName, List<Amenity> amenities) {
        return pointOfInterestRepository.getPointOfInterestsByStationAndAmenities(stationName, amenities).stream().map(PointOfInterestDTO::preparePOIDTO).collect(Collectors.toList());
    }

    @Override
    public List<Amenity> getAmenitiesById(List<Long> amenityIdList) {
        if (amenityIdList.isEmpty())
            return amenityRepository.findAllAmenities();
        return amenityRepository.findAllAmenitiesById(amenityIdList);
    }
}
