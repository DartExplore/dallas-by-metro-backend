package com.dallasbymetro.backend.service;

import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.dto.StationDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.entity.StationColor;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.repository.AmenityRepository;
import com.dallasbymetro.backend.repository.PointOfInterestRepository;
import com.dallasbymetro.backend.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service(value = "stationService")
public class StationServiceImpl implements StationService {
    final PointOfInterestRepository pointOfInterestRepository;
    final StationRepository stationRepository;
    final AmenityRepository amenityRepository;

    public StationServiceImpl(PointOfInterestRepository pointOfInterestRepository, StationRepository stationRepository, AmenityRepository amenityRepository) {
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.stationRepository = stationRepository;
        this.amenityRepository = amenityRepository;
    }

    @Override
    public List<PointOfInterestDTO> getPOIs(List<Amenity> amenities) {
        Long amenityCount = (long) amenities.size();
        return pointOfInterestRepository.getPOIsByAmenities(amenities, amenityCount).stream()
                .map(PointOfInterestDTO::prepareDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StationDTO> getStationsByLines(List<String> lines) throws DartExploreException {
        List<StationColor> colors = new ArrayList<>();
        String invalidColorString = null;

        for (String line : lines) {
            try {
                StationColor color = StationColor.valueOf(line);
                colors.add(color);
            } catch (IllegalArgumentException e) {
                invalidColorString = line;
                break;
            }
        }

        if (invalidColorString != null) {
            throw new DartExploreException("'" + invalidColorString + "' is not a valid StationColor.");
        }

        return stationRepository.findStationsByColors(colors)
                .stream()
                .map(StationDTO::prepareStationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PointOfInterestDTO> getPOIsAtStation(Long stationId, List<Amenity> amenities) {
        return pointOfInterestRepository.getPointOfInterestsByStationAndAmenities(stationId, amenities).stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
    }

    @Override
    public List<Amenity> getAmenitiesById(List<Long> amenityIdList) {
        if (amenityIdList.isEmpty())
            return amenityRepository.findAllAmenities();
        return amenityRepository.findAllAmenitiesById(amenityIdList);
    }

    @Override
    public List<StationDTO> getAllStationsWithPOIs() {
        Iterable<Station> stations = stationRepository.findAll();
        List<StationDTO> stationDTOs = new ArrayList<>();

        for (Station station : stations) {
            StationDTO stationDTO = new StationDTO(station);
            List<PointOfInterest> pois = pointOfInterestRepository.findByStation(station);
            List<PointOfInterestDTO> poiDTOs = pois.stream().map(PointOfInterestDTO::prepareDTO).collect(Collectors.toList());
            stationDTO.setPointsOfInterest(poiDTOs);
            stationDTOs.add(stationDTO);
        }

        return stationDTOs;
    }

    @Override
    public List<PointOfInterestDTO> getPOIsById(List<Long> poiIds) throws DartExploreException {
        List<PointOfInterestDTO> poiList = new ArrayList<>();
        for (Long id : poiIds) {
            Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(id);
            if (optionalPoi.isPresent()) {
                poiList.add(PointOfInterestDTO.prepareDTO(optionalPoi.get()));
            } else {
                throw new DartExploreException("Point of Interest with id: " + id + " does not exist");
            }
        }
        return poiList;
    }
}
