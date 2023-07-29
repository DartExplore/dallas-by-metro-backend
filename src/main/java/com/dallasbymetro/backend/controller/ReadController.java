package com.dallasbymetro.backend.controller;

import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.dto.StationDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.exception.ElementNotFoundException;
import com.dallasbymetro.backend.repository.AmenityRepository;
import com.dallasbymetro.backend.service.AmenityService;
import com.dallasbymetro.backend.service.PointOfInterestService;
import com.dallasbymetro.backend.service.StationServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            throws DartExploreException, ElementNotFoundException {
        List<Long> poiIdList;
        try {
            poiIdList = (poiIdsString.isEmpty()) ? new ArrayList<>() :
                    Arrays.stream(poiIdsString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DartExploreException("POI input must be CSV numbers.");
        }

        List<PointOfInterestDTO> pointOfInterestList = pointOfInterestService.getPOIsByIds(poiIdList);
        return new ResponseEntity<>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/poi/amenity")
    ResponseEntity<List<PointOfInterestDTO>> getPOIs(@RequestParam("amenityIdList") String amenitiesString) throws DartExploreException, ElementNotFoundException {
        // probably move this first bit to a utility class later
        List<Long> amenityIdList;
        try {
            amenityIdList = (amenitiesString.isEmpty()) ? new ArrayList<>() :
                    Arrays.stream(amenitiesString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DartExploreException("Amenities input must be CSV numbers.");
        }


        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIs(amenityIdList);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/poi/{stationId}/amenity")
    ResponseEntity<List<PointOfInterestDTO>> getPOIsAtStation(@PathVariable Long stationId, @RequestParam("amenityIdList") String amenitiesString) throws DartExploreException {
        // probably move this first bit to a utility class later
        List<Long> amenityIdList;
        try {
            amenityIdList = (amenitiesString.isEmpty()) ? new ArrayList<>() :
                    Arrays.stream(amenitiesString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DartExploreException("Amenities input must be CSV numbers.");
        }

        List<Amenity> amenities = stationService.getAmenitiesById(amenityIdList);
        List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsAtStation(stationId, amenities);
        return new ResponseEntity<List<PointOfInterestDTO>>(pointOfInterestList, HttpStatus.OK);
    }

    @GetMapping(value = "/line")
    ResponseEntity<List<StationDTO>> getStationsByLines(
            @RequestParam("line")
            @Parameter(example = "RED,BLUE,GREEN") String linesString)
            throws DartExploreException, ElementNotFoundException {
        String[] linesStrings = linesString.split(",");
        List<StationDTO> stations = stationService.getStationsByLines(Arrays.asList(linesStrings));
        return new ResponseEntity<List<StationDTO>>(stations, HttpStatus.OK);
    }

    @GetMapping(value = "/amenities")
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

    @GetMapping(value = "/type")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> types = pointOfInterestService.getAllTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping(value = "/stations")
    public ResponseEntity<List<StationDTO>> getStationsByConnection(
            @RequestParam(value = "currentStation") Long currentStation,
            @RequestParam(value = "maxStationConnections") Integer maxStationConnections,
            @RequestParam(value = "amenityIds", required = false) String amenityIdsString,
            @RequestParam(value = "maxWalkTime", required = false) Integer maxWalkTime,
            @RequestParam(value = "returnStationsWithNoPOIs", defaultValue = "false") Boolean returnEmpty) throws DartExploreException, ElementNotFoundException {

        // Validate input parameters
        if ((currentStation == null && maxStationConnections != null) || (currentStation != null && maxStationConnections == null)) {
            throw new DartExploreException("Both currentStation and stationConnections must be provided together.");
        }

        List<Long> amenityIdList = new ArrayList<>();
        if (amenityIdsString != null && !amenityIdsString.isEmpty()) {
            try {
                amenityIdList = Arrays.stream(amenityIdsString.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new DartExploreException("Amenities input must be CSV numbers.");
            }
        }

        // Call the service with the new returnEmpty parameter
        List<StationDTO> stations = stationService.getStationsByConnection(currentStation, maxStationConnections, amenityIdList, maxWalkTime, returnEmpty);

        return ResponseEntity.ok(stations);
    }
}
