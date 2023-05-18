package com.dart.explore.repository;

import com.dart.explore.entity.Amenity;
import com.dart.explore.entity.PointOfInterest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointOfInterestRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PointOfInterestRepository pointOfInterestRepository;

    @Test
    public void whenGetPointOfInterestByAmenity_thenReturnPointOfInterest() {
        // given
        PointOfInterest civilPour = new PointOfInterest();
        civilPour.setName("Civil Pour");
        Amenity bikeParking = new Amenity();
        bikeParking.setAmenity("The Best Vibes");
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(bikeParking);
        civilPour.setAmenities(amenities);
        entityManager.persist(bikeParking);
        entityManager.persist(civilPour);
        entityManager.flush();

        // when
        List<PointOfInterest> foundPOIs = pointOfInterestRepository.getPOIs(amenities);

        // then
        assertThat(foundPOIs).hasSize(1);
        // assertThat(foundPOIs.get(0).getName()).isEqualTo(civilPour.getName());
    }

}