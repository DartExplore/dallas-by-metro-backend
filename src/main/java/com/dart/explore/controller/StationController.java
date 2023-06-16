package com.dart.explore.controller;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.StationColor;
import com.dart.explore.service.StationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/public")
public class StationController {
    @Autowired
    StationServiceImpl stationService;

    @GetMapping(value = {"/poi/amenity", "/poi/amenity/{amenitiesStringOpt}"})
    ResponseEntity<List<PointOfInterestDTO>> getPOIs(@PathVariable Optional<String> amenitiesStringOpt) {
        // probably move this first bit to a utility class later
        List<Long> amenityIdList = new ArrayList<>();
        if (amenitiesStringOpt.isPresent()) {
            // populate amenityIdList
            String amenitiesString = amenitiesStringOpt.get();
            amenityIdList = Arrays.stream(amenitiesString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        }
        List<Amenity> amenities = stationService.getAmenitiesById(amenityIdList);
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIs(amenities);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/station/{line}")
    ResponseEntity<List<StationDTO>> getStationsByLine(@PathVariable String line) {
        List<StationDTO> stations = stationService.getStationsByLine(StationColor.valueOf(line));
        return new ResponseEntity<List<StationDTO>>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/poi/{stationId}")
    ResponseEntity<List<PointOfInterestDTO>> getPOIsByStation(@PathVariable Long stationId) {
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsByStation(stationId);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = {"/poi/{stationId}/amenity", "/poi/{stationId}/amenity/{amenitiesStringOpt}"})
    ResponseEntity<List<PointOfInterestDTO>> getPOIsAtStation(@PathVariable Long stationId, @PathVariable Optional<String> amenitiesStringOpt) {
        // probably move this first bit to a utility class later
        List<Long> amenityIdList = new ArrayList<>();
        if (amenitiesStringOpt.isPresent()) {
            // populate amenityIdList
            String amenitiesString = amenitiesStringOpt.get();
            amenityIdList = Arrays.stream(amenitiesString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        }
        List<Amenity> amenities = stationService.getAmenitiesById(amenityIdList);
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsAtStation(stationId, amenities);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }
}
