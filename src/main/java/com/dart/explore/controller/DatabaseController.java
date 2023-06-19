package com.dart.explore.controller;

import com.dart.explore.dto.AmenityDTO;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.service.AmenityService;
import com.dart.explore.service.PointOfInterestService;
import com.dart.explore.dto.PointOfInterestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/private")
@Validated
@SecurityRequirement(name = "API Key")
public class DatabaseController {

    private final PointOfInterestService pointOfInterestService;
    private final AmenityService amenityService;

    @Autowired
    public DatabaseController(PointOfInterestService pointOfInterestService, AmenityService amenityService) {
        this.pointOfInterestService = pointOfInterestService;
        this.amenityService = amenityService;
    }

    @PostMapping(value = "/poi")
    public ResponseEntity<PointOfInterestDTO> createPOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
        PointOfInterestDTO poi = pointOfInterestService.addPointOfInterest(pointOfInterestDTO);
        return ResponseEntity.ok(poi);
    }

    @PutMapping(value = "/poi")
    public ResponseEntity<PointOfInterestDTO> updatePOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) throws DartExploreException {
        PointOfInterestDTO poi = pointOfInterestService.updatePointOfInterest(pointOfInterestDTO);
        return ResponseEntity.ok(poi);
    }

    @DeleteMapping(value = "/poi/{poiId}")
    public ResponseEntity<String> deletePOI(@PathVariable Long poiId) throws DartExploreException {
        pointOfInterestService.deletePointOfInterest(poiId);
        return ResponseEntity.ok("Point of interest with ID: " + poiId + " successfully deleted");
    }

    @PostMapping(value = "/amenity")
    public ResponseEntity<AmenityDTO> createAmenity(@Valid @RequestBody AmenityDTO amenityDTO) {
        AmenityDTO amenity = amenityService.addAmenity(amenityDTO);
        return ResponseEntity.ok(amenity);
    }

    @PutMapping(value = "/amenity")
    public ResponseEntity<AmenityDTO> updateAmenity(@Valid @RequestBody AmenityDTO amenityDTO) throws DartExploreException {
        AmenityDTO amenity = amenityService.updateAmenity(amenityDTO);
        return ResponseEntity.ok(amenity);
    }

    @DeleteMapping(value = "/amenity/{amenityId}")
    public ResponseEntity<String> deleteAmenity(@PathVariable Long amenityId) throws DartExploreException {
        amenityService.deleteAmenity(amenityId);
        return ResponseEntity.ok("Amenity: " + amenityId + " successfully deleted");
    }
}