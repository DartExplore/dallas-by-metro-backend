package com.dallasbymetro.backend.integration;


import com.dallasbymetro.backend.service.ImportService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private ImportService importService;

    @PostConstruct
    public void setup() throws Exception {
        if (importService.isDataLoaded()) {
            importService.loadData("station_colors.txt");
            importService.loadData("station_connections.txt");
            importService.loadData("stations.txt");
        }
    }
}

