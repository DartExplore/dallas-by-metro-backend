package com.dart.explore.integration;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.dart.explore.service.ImportService;

@Configuration
public class TestConfig {

    @Autowired
    private ImportService importService;

    @PostConstruct
    public void setup() throws Exception {
        if (!importService.isDataLoaded()) {
            importService.loadData("station_colors.txt");
            importService.loadData("station_connections.txt");
            importService.loadData("stations.txt");
        }
    }
}

