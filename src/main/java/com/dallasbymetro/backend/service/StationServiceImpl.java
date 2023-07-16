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
import com.dallasbymetro.backend.utility.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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
    public List<PointOfInterestDTO> getPOIs(List<Long> amenityIdList) throws DartExploreException {
        int amenityCount = amenityIdList.size();
        List<Amenity> amenities = (amenityCount > 0) ? amenityRepository.findAllAmenitiesById(amenityIdList) : new ArrayList<>();

        if (amenities.size() != amenityCount) // at least one invalid amenity
            throw new DartExploreException("At least one amenity in the list was invalid. Please correct and try again.");

        return ((amenityCount > 0) ? pointOfInterestRepository.getPOIsByAmenities(amenities, amenityCount).stream() : // gets POI by amenities
                StreamSupport.stream(pointOfInterestRepository.findAll().spliterator(), false)) // gets all POIs if no amenities
                .map(PointOfInterestDTO::prepareDTO) // prepare DTO
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

    @Override
    public List<StationDTO> getStationsByConnection(Long currentStation, Integer stationConnections, List<Long> amenityIdList, Integer maxWalkTime) throws DartExploreException {
        Optional<Station> stationOptional = stationRepository.findByStationId(currentStation);

        if (stationOptional.isEmpty()) {
            throw new DartExploreException("Current Station with id: " + currentStation + " does not exist");
        }

        Station station = stationOptional.get();

        // Perform BFS traversal in the service layer
        List<Station> stationsWithinConnection = findStationsWithinConnection(station, stationConnections);

        if (!amenityIdList.isEmpty()) {
            // Filter stations by checking if the associated POIs have all the amenities specified.
            stationsWithinConnection = stationsWithinConnection.stream()
                    .filter(s -> PointOfInterestService.doAllPOIsHaveAmenities(s.getPointOfInterest(), amenityIdList))
                    .collect(Collectors.toList());
        }

        if (maxWalkTime != null) {
            stationsWithinConnection = stationsWithinConnection.stream()
                    .filter(s -> PointOfInterestService.doAllPOIsWithinWalkTime(s.getPointOfInterest(), maxWalkTime))
                    .collect(Collectors.toList());
        }

        // Transform stations to StationDTOs

        return stationsWithinConnection.stream()
                .map(s -> prepareStationDTOWithFilteredPOIs(s, maxWalkTime))
                .collect(Collectors.toList());
    }

    private StationDTO prepareStationDTOWithFilteredPOIs(Station station, Integer maxWalkTime) {
        if (maxWalkTime != null) {
            // Filter the POIs based on the max walk time
            List<PointOfInterest> filteredPOIs = station.getPointOfInterest().stream()
                    .filter(poi -> poi.getWalkingDistance() <= maxWalkTime)
                    .collect(Collectors.toList());
            station.setPointOfInterest(filteredPOIs);
        }

        return StationDTO.prepareStationDTO(station);
    }

    private List<Station> findStationsWithinConnection(Station currentStation, Integer stationConnections) {
        Queue<Pair<Station, Integer>> queue = new LinkedList<>();
        Map<Station, Integer> visitedMap = new HashMap<>();

        // Create a comparator to sort the pairs by their level and station name
        Comparator<Pair<Station, Integer>> pairComparator = Comparator
                .<Pair<Station, Integer>>comparingInt(Pair::getSecond)
                .thenComparing(pair -> pair.getFirst().getName());


        // Use a TreeSet to keep the pairs sorted
        Set<Pair<Station, Integer>> sortedVisitedPairs = new TreeSet<>(pairComparator);

        Pair<Station, Integer> firstPair = new Pair<>(currentStation, 0);
        queue.add(firstPair);
        visitedMap.put(currentStation, 0);
        sortedVisitedPairs.add(firstPair);

        while (!queue.isEmpty()) {
            Pair<Station, Integer> stationPair = queue.poll();
            Station station = stationPair.first;
            Integer level = stationPair.second;

            if (level < stationConnections) {
                List<Station> connectedStations = stationRepository.findConnectedStations(station.getStationId());

                for (Station connectedStation : connectedStations) {
                    if (!visitedMap.containsKey(connectedStation)) {
                        Pair<Station, Integer> nextPair = new Pair<>(connectedStation, level + 1);
                        visitedMap.put(connectedStation, level + 1);
                        queue.add(nextPair);
                        sortedVisitedPairs.add(nextPair);
                    }
                }
            }
        }

        // Transform the sorted set of pairs to a list of stations

        return sortedVisitedPairs.stream()
                .map(pair -> pair.first)
                .collect(Collectors.toList());
    }
}
