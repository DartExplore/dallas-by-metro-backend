package com.dart.explore.controller;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.repository.AmenityRepository;
import com.dart.explore.service.AmenityService;
import com.dart.explore.service.PointOfInterestService;
import com.dart.explore.service.StationServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
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
@CrossOrigin
public class ReadController {
    final StationServiceImpl stationService;
    private final PointOfInterestService pointOfInterestService;
    private final AmenityService amenityService;

    public ReadController(StationServiceImpl stationService, PointOfInterestService pointOfInterestService, AmenityRepository amenityRepository, AmenityService amenityService) {
        this.stationService = stationService;
        this.pointOfInterestService = pointOfInterestService;
        this.amenityService = amenityService;
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<StationDTO>> getAllStationsWithPOIs() {
        List<StationDTO> stationsWithPOIs = stationService.getAllStationsWithPOIs();
        return new ResponseEntity<List<StationDTO>>(stationsWithPOIs, HttpStatus.OK);
    }

    @GetMapping(value = "/poi")
    ResponseEntity<List<PointOfInterestDTO>> getPOIsById(
            @RequestParam("ID")
            @Parameter(example = "1,2,3") String poiIdsString)
            throws DartExploreException {
        String[] poiIdsStrings = poiIdsString.split(",");
        List<PointOfInterestDTO> pointOfInterestList = pointOfInterestService.getPOIsByIds(Arrays.asList(poiIdsStrings));
        return new ResponseEntity<>(pointOfInterestList, HttpStatus.OK);
    }

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

    @GetMapping(value = "/station")
    ResponseEntity<List<StationDTO>> getStationsByLines(
            @RequestParam("line")
            @Parameter(example = "RED,BLUE,GREEN") String linesString)
            throws DartExploreException {
        String[] linesStrings = linesString.split(",");
        List<StationDTO> stations = stationService.getStationsByLines(Arrays.asList(linesStrings));
        return new ResponseEntity<List<StationDTO>>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/amenities")
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

}
