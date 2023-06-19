package com.dart.explore.integration;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import com.dart.explore.repository.StationRepository;
import com.dart.explore.service.ImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ImportService.class)
public class StationRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ImportService importService;

    @Test
    public void whenFindByColors_thenReturnStations() {
        // given
        Station testStationGreen = new Station();
        testStationGreen.setName("This is a test station GREEN");
        testStationGreen.getColor().add(StationColor.GREEN);
        entityManager.persist(testStationGreen);

        Station testStationBlue = new Station();
        testStationBlue.setName("This is a test station BLUE");
        testStationBlue.getColor().add(StationColor.BLUE);
        entityManager.persist(testStationBlue);

        entityManager.flush();

        // when
        List<Station> found = stationRepository.findStationsByColors(Arrays.asList(StationColor.GREEN, StationColor.BLUE));

        // then
        // Adjusted count to reflect number of GREEN and BLUE stations
        assertThat(found).hasSize(31);

        // Adjusted to reflect names of the two test stations
        assertThat(found).extracting(Station::getName).contains(testStationGreen.getName(), testStationBlue.getName());
    }
}
