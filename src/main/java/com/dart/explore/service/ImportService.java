package com.dart.explore.service;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import com.dart.explore.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImportService {

    private StationRepository stationRepository;

    @Autowired
    public ImportService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void addStations(List<String[]> colorData, List<String[]> connectionData, List<String[]> stationData) {
        Map<String, Station> stations = new HashMap<>();

        for (String[] row : colorData) {
            String name = row[0];
            StationColor color = StationColor.valueOf(row[1].toUpperCase());

            Station station = stations.computeIfAbsent(name, k -> new Station());
            station.setName(name);
            station.getColor().add(color);

            stations.put(name, station);
        }

        for (String[] row : connectionData) {
            String station1Name = row[0];
            String station2Name = row[1];

            Station station1 = stations.get(station1Name);
            Station station2 = stations.get(station2Name);

            if (station1 != null && station2 != null) {
                station1.getConnectedStations().add(station2);
                station2.getConnectedStations().add(station1);
            }
        }

        for (String[] row : stationData) {
            String name = row[0];
            double lat = Double.parseDouble(row[1]);
            double lon = Double.parseDouble(row[2]);

            Station station = stations.get(name);
            if (station != null) {
                station.setLatitude(lat);
                station.setLongitude(lon);
            }
        }

        stationRepository.saveAll(stations.values());
    }
}
