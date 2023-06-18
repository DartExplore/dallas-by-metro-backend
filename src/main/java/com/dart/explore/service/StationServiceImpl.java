package com.dart.explore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.StationColor;
import com.dart.explore.entity.Station;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.repository.AmenityRepository;
import com.dart.explore.repository.PointOfInterestRepository;
import com.dart.explore.repository.StationRepository;
import org.springframework.stereotype.Service;

import com.dart.explore.entity.Amenity;


@Service(value = "stationService")
public class StationServiceImpl implements StationService {
    final PointOfInterestRepository pointOfInterestRepository;
    final StationRepository stationRepository;
    final AmenityRepository amenityRepository;

    public StationServiceImpl(PointOfInterestRepository pointOfInterestRepository, StationRepository stationRepository, AmenityRepository amenityRepository) {
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.stationRepository = stationRepository;
        this.amenityRepository = amenityRepository;
    }

    @Override
    public List<PointOfInterestDTO> getPOIs(List<Amenity> amenities) {
        return pointOfInterestRepository.getPOIsByAmenities(amenities).stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
    }

    @Override
    public List<StationDTO> getStationsByLine(StationColor color) {
        return stationRepository.findStationByColor(color).stream().map(StationDTO::prepareStationDTO).collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterestDTO> getPOIsByStation(Long stationId) {
        return pointOfInterestRepository.getPOIsByStation(stationId).stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterestDTO> getPOIsAtStation(Long stationId, List<Amenity> amenities) {
        return pointOfInterestRepository.getPointOfInterestsByStationAndAmenities(stationId, amenities).stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
    }

    @Override
    public List<Amenity> getAmenitiesById(List<Long> amenityIdList) {
        if (amenityIdList.isEmpty())
            return amenityRepository.findAllAmenities();
        return amenityRepository.findAllAmenitiesById(amenityIdList);
    }

    @Override
    public List<StationDTO> getAllStationsWithPOIs() {
        Iterable<Station> stations = stationRepository.findAll();
        List<StationDTO> stationDTOs = new ArrayList<>();

        for (Station station : stations) {
            StationDTO stationDTO = new StationDTO(station);
            List<PointOfInterest> pois = pointOfInterestRepository.findByStation(station);
            List<PointOfInterestDTO> poiDTOs = pois.stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
            stationDTO.setPointsOfInterest(poiDTOs);
            stationDTOs.add(stationDTO);
        }

        return stationDTOs;
    }

    @Override
    public List<PointOfInterestDTO> getPOIsById(List<Long> poiIds) throws DartExploreException {
        List<PointOfInterestDTO> poiList = new ArrayList<>();
        for (Long id : poiIds) {
            Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(id);
            if (optionalPoi.isPresent()) {
                poiList.add(PointOfInterestDTO.prepareDTO(optionalPoi.get()));
            } else {
                throw new DartExploreException("Point of Interest with id: " + id + " does not exist");
            }
        }
        return poiList;
    }

}
