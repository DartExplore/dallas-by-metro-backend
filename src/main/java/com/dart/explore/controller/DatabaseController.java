package com.dart.explore.controller;

import com.dart.explore.service.PointOfInterestService;
import com.dart.explore.dto.PointOfInterestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/private")
@Validated
public class DatabaseController {

    private final PointOfInterestService pointOfInterestService;

    @Autowired
    public DatabaseController(PointOfInterestService pointOfInterestService) {
        this.pointOfInterestService = pointOfInterestService;
    }

    @PostMapping(value = "/poi")
    public ResponseEntity<PointOfInterestDTO> createPOI(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) {
        if (pointOfInterestDTO.getStationId() == null) {
            throw new IllegalArgumentException("Station ID is required");
        }
        PointOfInterestDTO poi = pointOfInterestService.addPointOfInterest(pointOfInterestDTO);
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
}