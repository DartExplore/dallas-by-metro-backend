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

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StationController {
    @Autowired
    StationServiceImpl stationService;

    @GetMapping(value = "/public/poi")
    ResponseEntity<List<PointOfInterestDTO>> getPOIs(@RequestBody List<Amenity> amenities) {
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIs(amenities);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/public/station/{line}")
    ResponseEntity<List<StationDTO>> getStationsByLine(@PathVariable String line) {
        List<StationDTO> stations = stationService.getStationsByLine(StationColor.valueOf(line));
        return new ResponseEntity<List<StationDTO>>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/public/poi/{station}")
    ResponseEntity<List<PointOfInterestDTO>> getPOIsByStation(@PathVariable String station) {
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsByStation(station);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/public/poi/{station}/amenity")
    ResponseEntity<List<PointOfInterestDTO>> getPOIsAtStation(@PathVariable String station, @RequestBody List<Amenity> amenities) {
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsAtStation(station, amenities);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }
}
