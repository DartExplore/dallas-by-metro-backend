package com.dallasbymetro.backend.service;

import com.dallasbymetro.backend.dto.AmenityDTO;
import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.exception.ElementNotFoundException;
import com.dallasbymetro.backend.repository.AmenityRepository;
import com.dallasbymetro.backend.repository.PointOfInterestRepository;
import com.dallasbymetro.backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public PointOfInterestDTO addPointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws ElementNotFoundException {
        Optional<Station> optionalStation = stationRepository.findByStationId(pointOfInterestDTO.getStationId());

        if (optionalStation.isEmpty()) {
            throw new ElementNotFoundException("Station with id: " + pointOfInterestDTO.getStationId() + " does not exist");
        }

        // getting amenities; if not every one is present we fail
        List<Amenity> amenityList = amenityRepository.findAllAmenitiesById(pointOfInterestDTO.getAmenities()
                .stream().map(AmenityDTO::getAmenityId).collect(Collectors.toList()));

        if (amenityList.size() != pointOfInterestDTO.getAmenities().size()) {
            throw new ElementNotFoundException("At least one invalid amenityId present. Check your input.");
        }

        // Convert DTO to Entity using prepareEntity method
        PointOfInterest poi = PointOfInterestDTO.prepareEntity(pointOfInterestDTO, optionalStation.get(), amenityList);
        // Save to the database
        PointOfInterest savedPoi = pointOfInterestRepository.save(poi);
        // Convert back to DTO and return
        return PointOfInterestDTO.prepareDTO(savedPoi);
    }

    public PointOfInterestDTO updatePointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws ElementNotFoundException {
        Optional<Station> optionalStation = stationRepository.findByStationId(pointOfInterestDTO.getStationId());

        if (optionalStation.isEmpty()) {
            throw new ElementNotFoundException("Station with id: " + pointOfInterestDTO.getStationId() + " does not exist");
        }

        // Check if the Point of Interest exists in the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(pointOfInterestDTO.getPoiId());

        if (optionalPoi.isEmpty()) {
            throw new ElementNotFoundException("Point of Interest with id: " + pointOfInterestDTO.getPoiId() + " does not exist");
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
                throw new ElementNotFoundException("At least one invalid amenityId present. Check your input.");
            }

            poiEntity.setAmenities(amenityList);
        }

        // Save updated entity to the database
        PointOfInterest updatedPoi = pointOfInterestRepository.save(poiEntity);

        // Convert updated entity back to DTO and return
        return PointOfInterestDTO.prepareDTO(updatedPoi);
    }


    public void deletePointOfInterest(Long poiId) throws ElementNotFoundException {
        // Fetch the point of interest from the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(poiId);

        if (optionalPoi.isEmpty()) {
            throw new ElementNotFoundException("Point of Interest with id: " + poiId + " does not exist");
        }

        pointOfInterestRepository.deleteById(poiId);
    }

    public List<PointOfInterestDTO> getPOIsByIds(List<Long> poiIdList) throws ElementNotFoundException {
        List<PointOfInterest> poiList = (!poiIdList.isEmpty()) ? StreamSupport.stream(pointOfInterestRepository.findAllById(poiIdList).spliterator(), false).toList()
                : new ArrayList<>();

        if (poiIdList.size() != poiList.size()) // at least one invalid POI
            throw new ElementNotFoundException("At least one POI in the list was invalid. Please correct and try again.");

        return poiList.stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
    }

    public List<String> getAllTypes() {
        return pointOfInterestRepository.getAllTypes();
    }

    public static boolean doPOIHaveAmenities(PointOfInterest poi, List<Long> amenityIds) {
        if (amenityIds == null || amenityIds.isEmpty()) {
            return true;  // If no amenities are specified, all POIs are allowed
        } else {
            return poi.getAmenities().stream().map(Amenity::getAmenityId).collect(Collectors.toSet()).containsAll(amenityIds);
        }
    }

}
