package com.dart.explore.controller;

import com.dart.explore.dto.PointOfInterestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/private")
@Validated
public class DatabaseController {
    @PostMapping(value = "/poi")
    public ResponseEntity<String> createUser(@Valid @RequestBody PointOfInterestDTO pointOfInterestDTO) {
        return ResponseEntity.ok("Point of interest created successfully");
    }
}
