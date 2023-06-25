package com.dallasbymetro.backend.service;

import com.dallasbymetro.backend.dto.AmenityDTO;
import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.repository.AmenityRepository;
import com.dallasbymetro.backend.repository.PointOfInterestRepository;
import com.dallasbymetro.backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointOfInterestService {

    private final PointOfInterestRepository pointOfInterestRepository;
    private final StationRepository stationRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public PointOfInterestService(PointOfInterestRepository pointOfInterestRepository, StationRepository stationRepository, AmenityRepository amenityRepository) {
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.stationRepository = stationRepository;
        this.amenityRepository = amenityRepository;
    }

    public PointOfInterestDTO addPointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
        Optional<Station> optionalStation = stationRepository.findByStationIdIs(pointOfInterestDTO.getStationId());

        if (optionalStation.isEmpty()) {
            throw new DartExploreException("Station with id: " + pointOfInterestDTO.getStationId() + " does not exist");
        }

        // getting amenities; if not every one is present we fail
        List<Amenity> amenityList = amenityRepository.findAllAmenitiesById(pointOfInterestDTO.getAmenities()
                .stream().map(AmenityDTO::getAmenityId).collect(Collectors.toList()));

        if (amenityList.size() != pointOfInterestDTO.getAmenities().size()) {
            throw new DartExploreException("At least one invalid amenityId present. Check your input.");
        }

        // Convert DTO to Entity using prepareEntity method
        PointOfInterest poi = PointOfInterestDTO.prepareEntity(pointOfInterestDTO, optionalStation.get(), amenityList);
        // Save to the database
        PointOfInterest savedPoi = pointOfInterestRepository.save(poi);
        // Convert back to DTO and return
        return PointOfInterestDTO.prepareDTO(savedPoi);
    }

    public PointOfInterestDTO updatePointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
        Optional<Station> optionalStation = stationRepository.findByStationIdIs(pointOfInterestDTO.getStationId());

        if (optionalStation.isEmpty()) {
            throw new DartExploreException("Station with id: " + pointOfInterestDTO.getStationId() + " does not exist");
        }

        // Check if the Point of Interest exists in the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(pointOfInterestDTO.getPoiId());

        if (optionalPoi.isEmpty()) {
            throw new DartExploreException("Point of Interest with id: " + pointOfInterestDTO.getPoiId() + " does not exist");
        }

        // Update the entity from DTO
        PointOfInterest poiEntity = optionalPoi.get();

        if (pointOfInterestDTO.getName() != null) {
            poiEntity.setName(pointOfInterestDTO.getName());
        }

        if (pointOfInterestDTO.getLocation() != null) {
            poiEntity.setLocation(pointOfInterestDTO.getLocation());
        }

        if (pointOfInterestDTO.getType() != null) {
            poiEntity.setType(pointOfInterestDTO.getType());
        }

        if (pointOfInterestDTO.getWalkingDistance() != null) {
            poiEntity.setWalkingDistance(pointOfInterestDTO.getWalkingDistance());
        }

        if (pointOfInterestDTO.getPicUrl() != null) {
            poiEntity.setPicUrl(pointOfInterestDTO.getPicUrl());
        }


        if (pointOfInterestDTO.getAmenities() != null) {
            // getting amenities; if not every one is present we fail
            List<Amenity> amenityList = amenityRepository.findAllAmenitiesById(pointOfInterestDTO.getAmenities()
                    .stream().map(AmenityDTO::getAmenityId).collect(Collectors.toList()));

            if (amenityList.size() != pointOfInterestDTO.getAmenities().size()) {
                throw new DartExploreException("At least one invalid amenityId present. Check your input.");
            }

            poiEntity.setAmenities(amenityList);
        }

        // Save updated entity to the database
        PointOfInterest updatedPoi = pointOfInterestRepository.save(poiEntity);

        // Convert updated entity back to DTO and return
        return PointOfInterestDTO.prepareDTO(updatedPoi);
    }


    public void deletePointOfInterest(Long poiId) throws DartExploreException {
        // Fetch the point of interest from the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(poiId);

        if (optionalPoi.isEmpty()) {
            throw new DartExploreException("Point of Interest with id: " + poiId + " does not exist");
        }

        pointOfInterestRepository.deleteById(poiId);
    }

    public List<PointOfInterestDTO> getPOIsByIds(List<String> poiIdsStrings) throws DartExploreException {
        List<Long> poiIds = new ArrayList<>();
        String invalidNumberString = null;
        try {
            for (String poiIdString : poiIdsStrings) {
                try {
                    Long poiId = Long.parseLong(poiIdString);
                    poiIds.add(poiId);
                } catch (NumberFormatException e) {
                    invalidNumberString = poiIdString;
                    break;
                }
            }
            if (invalidNumberString != null) {
                throw new DartExploreException("'" + invalidNumberString + "' is not a valid number.");
            }

            List<PointOfInterest> poiList = new ArrayList<>();
            pointOfInterestRepository.findAllById(poiIds).forEach(poiList::add);

            return poiList.stream()
                    .map(PointOfInterestDTO::prepareDTO)
                    .collect(Collectors.toList());

        } catch (NumberFormatException e) {
            throw new DartExploreException("'" + invalidNumberString + "' is not a valid number.");
        }
    }
}
