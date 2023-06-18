package com.dart.explore;

import com.dart.explore.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExploreApplication implements CommandLineRunner {

    final ImportService importService;

    private static final Logger log = LoggerFactory.getLogger(ExploreApplication.class);

    public ExploreApplication(ImportService importService) {
        this.importService = importService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExploreApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            if (importService.isDataLoaded()) {
                List<String[]> colorData = importService.loadData("station_colors.txt");
                List<String[]> connectionData = importService.loadData("station_connections.txt");
                List<String[]> stationData = importService.loadData("stations.txt");

                importService.addStations(colorData, connectionData, stationData);
            }
        } catch (IOException e) {
            log.error("Error while loading station data: ", e);
        }
    }
}
