package com.dart.explore.controller;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.entity.Station;
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
    ResponseEntity<List<PointOfInterest>> getPOIs(@RequestBody List<Amenity> amenities) {
        List<PointOfInterest> pointOfInterestList = stationService.getPOIs(amenities);
        return new ResponseEntity<List<PointOfInterest>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/public/station/{line}")
    ResponseEntity<List<Station>> getStationsByLine(@PathVariable String line) {
        List<Station> stations = stationService.getStationsByLine(StationColor.valueOf(line));
        return new ResponseEntity<List<Station>>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/public/poi/{station}")
    ResponseEntity<List<PointOfInterest>> getPOIsByStation(@PathVariable String station) {
        List<PointOfInterest> pointOfInterestList = stationService.getPOIsByStation(station);
        return new ResponseEntity<List<PointOfInterest>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/public/poi/{station}/amenity")
    ResponseEntity<List<PointOfInterest>> getPOIsAtStation(@PathVariable String station, @RequestBody List<Amenity> amenities) {
        List<PointOfInterest> pointOfInterestList = stationService.getPOIsAtStation(station, amenities);
        return new ResponseEntity<List<PointOfInterest>>(pointOfInterestList, HttpStatus.OK);
    }
}
