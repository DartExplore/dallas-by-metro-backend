package com.dart.explore;

import com.dart.explore.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExploreApplication implements CommandLineRunner {

    @Autowired
    ImportService importService;

    private static final Logger log = LoggerFactory.getLogger(ExploreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ExploreApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            List<String[]> colorData = loadData("station_colors.txt");
            List<String[]> connectionData = loadData("station_connections.txt");
            List<String[]> stationData = loadData("stations.txt");

            importService.addStations(colorData, connectionData, stationData);
        } catch (IOException e) {
            log.error("Error while loading station data: ", e);
        }
    }

    private List<String[]> loadData(String filename) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new ClassPathResource(filename).getFile()))) {
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }
                data.add(fields);
            }
        }
        return data;
    }
}
