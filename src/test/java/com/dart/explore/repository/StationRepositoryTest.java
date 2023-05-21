package com.dart.explore.repository;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void whenFindByColor_thenReturnStations() {
        // given
        Station grandCentral = new Station();
        grandCentral.setName("Grand Central");
        grandCentral.getColor().add(StationColor.GREEN);
        entityManager.persist(grandCentral);
        entityManager.flush();

        // when
        List<Station> found = stationRepository.getStationsByLine(StationColor.GREEN);

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo(grandCentral.getName());
    }
}
