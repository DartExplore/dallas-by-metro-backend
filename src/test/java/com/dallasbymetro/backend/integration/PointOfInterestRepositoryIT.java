package com.dallasbymetro.backend.integration;

import com.dallasbymetro.backend.entity.Amenity;
import com.dallasbymetro.backend.entity.PointOfInterest;
import com.dallasbymetro.backend.entity.Station;
import com.dallasbymetro.backend.repository.PointOfInterestRepository;
import com.dallasbymetro.backend.service.ImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ImportService.class, TestConfig.class})
class PointOfInterestRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private ImportService importService;

    private PointOfInterest testPOI;
    private Amenity testAmenity;
    private Station testStation;

    @BeforeEach
    public void setup() {
        testPOI = new PointOfInterest();
        testPOI.setName("Test POI");
        testAmenity = new Amenity();
        testAmenity.setAmenity("Test Amenity");
        testStation = new Station();
        testStation.setName("Test Station");
        testPOI.setStation(testStation);
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(testAmenity);
        testPOI.setAmenities(amenities);
        entityManager.persist(testAmenity);
        entityManager.persist(testStation);
        entityManager.persist(testPOI);
        entityManager.flush();
    }

    @Test
    public void whenGetPointOfInterestByAmenity_thenReturnPointOfInterest() {
        List<Amenity> amenities = Collections.singletonList(testAmenity);
        Integer amenityCount = amenities.size();
        List<PointOfInterest> foundPOIs = pointOfInterestRepository.getPOIsByAmenities(amenities, amenityCount);

        assertThat(foundPOIs).hasSize(1);
        assertThat(foundPOIs.get(0).getName()).isEqualTo(testPOI.getName());
    }

    @Test
    public void whenGetPointOfInterestsByStationAndAmenities_thenReturnPointOfInterests() {
        List<PointOfInterest> foundPOIs = pointOfInterestRepository.getPointOfInterestsByStationAndAmenities(testStation.getStationId(), Collections.singletonList(testAmenity));
        assertThat(foundPOIs).hasSize(1);
        assertThat(foundPOIs.get(0).getName()).isEqualTo(testPOI.getName());
    }
}
