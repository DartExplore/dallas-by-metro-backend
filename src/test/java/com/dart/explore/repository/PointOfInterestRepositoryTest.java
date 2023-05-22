package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import com.dart.explore.service.ImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointOfInterestRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PointOfInterestRepository pointOfInterestRepository;

    @MockBean
    private ImportService importService;

    @Test
    public void whenGetPointOfInterestByAmenity_thenReturnPointOfInterest() {
        // given
        PointOfInterest testPOI = new PointOfInterest();
        testPOI.setName("Test POI");
        Amenity testAmenity = new Amenity();
        testAmenity.setAmenity("Test Amenity");
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(testAmenity);
        testPOI.setAmenities(amenities);
        entityManager.persist(testAmenity);
        entityManager.persist(testPOI);
        entityManager.flush();

        // when
        List<PointOfInterest> foundPOIs = pointOfInterestRepository.getPOIsByAmenities(amenities);

        // then
        assertThat(foundPOIs).hasSize(1);
        // assertThat(foundPOIs.get(0).getName()).isEqualTo(testPOI.getName());
    }

}