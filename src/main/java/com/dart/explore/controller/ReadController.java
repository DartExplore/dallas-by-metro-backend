package com.dart.explore.controller;

import com.dart.explore.dto.PointOfInterestDTO;
import com.dart.explore.dto.StationDTO;
import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.StationColor;
import com.dart.explore.exception.DartExploreException;
import com.dart.explore.service.StationServiceImpl;
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
public class ReadController {
    final StationServiceImpl stationService;

    public ReadController(StationServiceImpl stationService) {
        this.stationService = stationService;
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<StationDTO>> getAllStationsWithPOIs() {
        List<StationDTO> stationsWithPOIs = stationService.getAllStationsWithPOIs();
        return new ResponseEntity<List<StationDTO>>(stationsWithPOIs, HttpStatus.OK);
    }

    @GetMapping(value = "/pois")
    ResponseEntity<Object> getPOIsById(@RequestParam("ID") String poiIdsString) {
        List<Long> poiIds = new ArrayList<>();
        String[] poiIdsStrings = poiIdsString.split(",");
        String invalidNumberString = null;

        try {
            for (String poiIdString : poiIdsStrings) {
                // Try to parse each string to a Long
                try {
                    Long poiId = Long.parseLong(poiIdString);
                    poiIds.add(poiId);
                } catch (NumberFormatException e) {
                    // If parsing fails, store the invalid string and break the loop
                    invalidNumberString = poiIdString;
                    break;
                }
            }

            if (invalidNumberString != null) {
                // If there was an invalid string, throw a NumberFormatException manually
                throw new NumberFormatException();
            }

            // Retrieve the POIs with the given IDs from service layer
            List<PointOfInterestDTO> pointOfInterestList = stationService.getPOIsById(poiIds);

            // Return the POIs in the response
            return new ResponseEntity<>(pointOfInterestList, HttpStatus.OK);
        } catch (NumberFormatException e) {
            // Return an error response if any of the IDs could not be parsed to a Long
            return new ResponseEntity<>("'" + invalidNumberString + "' is not a valid number.", HttpStatus.BAD_REQUEST);
        } catch (DartExploreException e) {
            // Return an error response if any of the POIs could not be found
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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

    @GetMapping(value = "/station/{line}")
    ResponseEntity<List<StationDTO>> getStationsByLine(@PathVariable String line) {
        List<StationDTO> stations = stationService.getStationsByLine(StationColor.valueOf(line));
        return new ResponseEntity<List<StationDTO>>(stations, HttpStatus.OK);
    }

}
