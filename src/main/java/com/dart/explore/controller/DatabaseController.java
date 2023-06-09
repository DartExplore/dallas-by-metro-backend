package com.dart.explore.controller;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.service.AmenityService;
import com.dart.explore.service.PointOfInterestService;
import com.dart.explore.dto.PointOfInterestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/private")
@Validated
public class DatabaseController {

    private final PointOfInterestService pointOfInterestService;
    private final AmenityService amenityService;

    @Autowired
    public DatabaseController(PointOfInterestService pointOfInterestService, AmenityService amenityService) {
        this.pointOfInterestService = pointOfInterestService;
        this.amenityService = amenityService;
    }

    @PostMapping(value = "/poi")
    public ResponseEntity<PointOfInterestDTO> createPOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) {
        if (pointOfInterestDTO.getStationId() == null) {
            throw new IllegalArgumentException("Station ID is required");
        }
        PointOfInterestDTO poi = pointOfInterestService.addPointOfInterest(pointOfInterestDTO);
        return ResponseEntity.ok(poi);
    }

    @PutMapping(value = "/poi")
    public ResponseEntity<PointOfInterestDTO> updatePOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) {
        PointOfInterestDTO poi = pointOfInterestService.updatePointOfInterest(pointOfInterestDTO);
        return ResponseEntity.ok(poi);
    }

    @DeleteMapping(value = "/poi")
    public ResponseEntity<String> deletePOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) {
        if (pointOfInterestDTO.getPoiId() == null) {
            throw new IllegalArgumentException("poiId is required");
        }
        String message = pointOfInterestService.deletePointOfInterest(pointOfInterestDTO);
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/amenity")
    public ResponseEntity<AmenityDTO> createAmenity(@Valid @RequestBody AmenityDTO amenityDTO) {
        AmenityDTO poi = amenityService.addAmenity(amenityDTO);
        return ResponseEntity.ok(poi);
    }

    @PutMapping(value = "/amenity")
    public ResponseEntity<AmenityDTO> updateAmenity(@Valid @RequestBody AmenityDTO amenityDTO) {
        AmenityDTO amenity = amenityService.updateAmenity(amenityDTO);
        return ResponseEntity.ok(amenity);
    }

    @DeleteMapping(value = "/amenity")
    public ResponseEntity<String> deleteAmenity(@Valid @RequestBody AmenityDTO amenityDTO) {
        if (amenityDTO.getAmenityId() == null) {
            throw new IllegalArgumentException("amenityId is required");
        }
        String message = amenityService.deleteAmenity(amenityDTO);
        return ResponseEntity.ok(message);
    }
}