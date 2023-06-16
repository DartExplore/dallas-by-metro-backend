package com.dart.explore.service;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.repository.PointOfInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointOfInterestService {

    private final PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    public PointOfInterestService(PointOfInterestRepository pointOfInterestRepository) {
        this.pointOfInterestRepository = pointOfInterestRepository;
    }

    public PointOfInterestDTO addPointOfInterest(PointOfInterestDTO pointOfInterestDTO) {
        // Convert DTO to Entity using prepareEntity method
        PointOfInterest poi = PointOfInterestDTO.prepareEntity(pointOfInterestDTO);
        // Save to the database
        PointOfInterest savedPoi = pointOfInterestRepository.save(poi);
        // Convert back to DTO and return
        return PointOfInterestDTO.prepareDTO(savedPoi);
    }

    public PointOfInterestDTO updatePointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
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
            List<Amenity> amenities = pointOfInterestDTO.getAmenities()
                    .stream()
                    .map(AmenityDTO::prepareAmenityEntity)
                    .collect(Collectors.toList());
            poiEntity.setAmenities(amenities);
        }

        // Save updated entity to the database
        PointOfInterest updatedPoi = pointOfInterestRepository.save(poiEntity);

        // Convert updated entity back to DTO and return
        return PointOfInterestDTO.prepareDTO(updatedPoi);
    }


    public void deletePointOfInterest(PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
        // Fetch the point of interest from the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(pointOfInterestDTO.getPoiId());

        if (optionalPoi.isEmpty()) {
            throw new DartExploreException("Point of Interest with id: " + pointOfInterestDTO.getPoiId() + " does not exist");
        }

        pointOfInterestRepository.deleteById(pointOfInterestDTO.getPoiId());
    }
}
