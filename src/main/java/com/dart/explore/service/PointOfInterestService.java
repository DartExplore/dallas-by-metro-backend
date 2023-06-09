package com.dart.explore.service;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
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
        PointOfInterest poi = PointOfInterestDTO.preparePOIEntity(pointOfInterestDTO);
        // Save to the database
        PointOfInterest savedPoi = pointOfInterestRepository.save(poi);
        // Convert back to DTO and return
        return PointOfInterestDTO.preparePOIDTO(savedPoi);
    }

    public PointOfInterestDTO updatePointOfInterest(PointOfInterestDTO pointOfInterestDTO) {
        // Check if the Point of Interest exists in the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(pointOfInterestDTO.getPoiId());

        if (optionalPoi.isEmpty()) {
            throw new IllegalArgumentException("Point of Interest with id: " + pointOfInterestDTO.getPoiId() + " does not exist");
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
        return PointOfInterestDTO.preparePOIDTO(updatedPoi);
    }


    public String deletePointOfInterest(PointOfInterestDTO pointOfInterestDTO) {
        // Fetch the point of interest from the database
        Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(pointOfInterestDTO.getPoiId());

        if (optionalPoi.isEmpty()) {
            return "Point of Interest with id: " + pointOfInterestDTO.getPoiId() + " does not exist";
        }

        // Store the name before deletion
        String poiName = optionalPoi.get().getName();

        // Delete the entity from the database
        pointOfInterestRepository.deleteById(pointOfInterestDTO.getPoiId());

        // Check if the entity still exists in the database
        if (pointOfInterestRepository.existsById(pointOfInterestDTO.getPoiId())) {
            return "Failed to delete point of interest: " + poiName;
        } else {
            return "Point of interest: " + poiName + " successfully deleted";
        }
    }
}
