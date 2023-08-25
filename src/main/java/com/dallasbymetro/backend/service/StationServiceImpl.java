package com.dallasbymetro.backend.service;

import com.dallasbymetro.backend.dto.PointOfInterestDTO;
import com.dallasbymetro.backend.dto.StationDTO;
import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.entity.StationColor;
import com.dallasbymetro.backend.exception.DartExploreException;
import com.dallasbymetro.backend.exception.ElementNotFoundException;
import com.dallasbymetro.backend.repository.AmenityRepository;
import com.dallasbymetro.backend.repository.PointOfInterestRepository;
import com.dallasbymetro.backend.repository.StationRepository;
import com.dallasbymetro.backend.utility.StationNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    public List<PointOfInterestDTO> getPOIs(List<Long> amenityIdList) throws ElementNotFoundException {
        int amenityCount = amenityIdList.size();
        List<Amenity> amenities = (amenityCount > 0) ? amenityRepository.findAllAmenitiesById(amenityIdList) : new ArrayList<>();

        if (amenities.size() != amenityCount) // at least one invalid amenity
            throw new ElementNotFoundException("At least one amenity in the list was invalid. Please correct and try again.");

        return ((amenityCount > 0) ? pointOfInterestRepository.getPOIsByAmenities(amenities, amenityCount).stream() : // gets POI by amenities
                StreamSupport.stream(pointOfInterestRepository.findAll().spliterator(), false)) // gets all POIs if no amenities
                .map(PointOfInterestDTO::prepareDTO) // prepare DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<StationDTO> getStationsByLines(List<String> lines) throws ElementNotFoundException {
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
            throw new ElementNotFoundException("'" + invalidColorString + "' is not a valid StationColor.");
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
    public List<PointOfInterestDTO> getPOIsById(List<Long> poiIds) throws ElementNotFoundException {
        List<PointOfInterestDTO> poiList = new ArrayList<>();
        for (Long id : poiIds) {
            Optional<PointOfInterest> optionalPoi = pointOfInterestRepository.findById(id);
            if (optionalPoi.isPresent()) {
                poiList.add(PointOfInterestDTO.prepareDTO(optionalPoi.get()));
            } else {
                throw new ElementNotFoundException("Point of Interest with id: " + id + " does not exist");
            }
        }
        return poiList;
    }

    @Override
    public List<StationDTO> getStationsByConnection(Long currentStation, Integer stationConnections, Integer maxTransfers, List<Long> amenityIdList, List<String> typesList, Integer maxWalkTime, Boolean returnEmpty) throws ElementNotFoundException, DartExploreException {
        if ((currentStation == null && stationConnections != null) || (currentStation != null && stationConnections == null)) {
            throw new DartExploreException("Both currentStation and stationConnections must be provided together.");
        }

        if (currentStation == null) { // This implicitly means stationConnections is also null due to the above check
            List<Station> allStations = (List<Station>) stationRepository.findAll();
            Stream<StationDTO> stream = allStations.stream()
                    .map(s -> prepareStationDTOWithFilteredPOIs(s, amenityIdList, typesList, maxWalkTime));

            // Apply the filtering based on the returnEmpty flag
            if (!returnEmpty) {
                stream = stream.filter(s -> !s.getPointsOfInterest().isEmpty());
            }

            return stream.collect(Collectors.toList());
        }

        Optional<Station> stationOptional = stationRepository.findByStationId(currentStation);

        if (stationOptional.isEmpty()) {
            throw new ElementNotFoundException("Current Station with id: " + currentStation + " does not exist");
        }

        Station station = stationOptional.get();

        // Perform BFS traversal
        List<StationNode> stationNodesWithinConnection = findStationsWithinConnection(station, stationConnections, maxTransfers);
        stationNodesWithinConnection.sort(Comparator.comparingInt(node -> node.level));

        // Transform stations to StationDTOs, check that POI have required amenities, and are within walk time
        Stream<StationDTO> stream = stationNodesWithinConnection.stream()
                .map(node -> prepareStationDTOWithFilteredPOIs(node.station, amenityIdList, typesList, maxWalkTime));

        // Apply the filtering based on the returnEmpty flag
        if (!returnEmpty) {
            stream = stream.filter(s -> !s.getPointsOfInterest().isEmpty());
        }

        return stream.collect(Collectors.toList());
    }

    private StationDTO prepareStationDTOWithFilteredPOIs(Station station, List<Long> amenityIdList, List<String> typesList, Integer maxWalkTime) {
        List<PointOfInterest> filteredPOIs;

        if ((amenityIdList == null || amenityIdList.isEmpty()) && (typesList == null || typesList.isEmpty()) && maxWalkTime == null) {
            filteredPOIs = station.getPointOfInterest();
        } else {
            filteredPOIs = station.getPointOfInterest().stream()
                    .filter(poi -> PointOfInterestService.doPOIHaveAmenities(poi, amenityIdList))
                    .filter(poi -> typesList == null || typesList.isEmpty() || typesList.contains(poi.getType()))
                    .filter(poi -> maxWalkTime == null || poi.getWalkingDistance() != null && poi.getWalkingDistance() <= maxWalkTime)
                    .collect(Collectors.toList());
        }

        station.setPointOfInterest(filteredPOIs);
        return StationDTO.prepareStationDTO(station);
    }

    private List<StationNode> findStationsWithinConnection(Station currentStation, Integer stationConnections, Integer maxTransfers) {
        Queue<StationNode> queue = new LinkedList<>();
        Set<Station> visitedSet = new HashSet<>();
        List<StationNode> resultList = new ArrayList<>();

        if (maxTransfers == null) {
            maxTransfers = 0;
        }


        // Enqueue current station with each of its colors
        for (StationColor color : currentStation.getColor()) {
            StationNode firstNode = new StationNode(currentStation, 0, 0, Set.of(color));
            queue.add(firstNode);
        }

        visitedSet.add(currentStation);

        while (!queue.isEmpty()) {
            StationNode currentNode = queue.poll();
            Station station = currentNode.station;
            int currentLevel = currentNode.level;
            int transferCount = currentNode.transferCount;
            StationColor currentColor = currentNode.colors.iterator().next();

            if (currentLevel < stationConnections) {
                List<Station> connectedStations = stationRepository.findConnectedStations(station.getStationId());

                for (Station connectedStation : connectedStations) {
                    if (!visitedSet.contains(connectedStation)) {
                        boolean requiresTransfer = !connectedStation.getColor().contains(currentColor);
                        int newTransferCount = requiresTransfer ? transferCount + 1 : transferCount;

                        if (newTransferCount <= maxTransfers) {
                            StationColor nextColor = requiresTransfer ? connectedStation.getColor().iterator().next() : currentColor;
                            StationNode nextNode = new StationNode(connectedStation, currentLevel + 1, newTransferCount, Set.of(nextColor));

                            visitedSet.add(connectedStation);
                            queue.add(nextNode);
                            resultList.add(nextNode);
                        }
                    }
                }
            }
        }

        return resultList;
    }
}
