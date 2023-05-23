package com.dart.explore.repository;

import com.dart.explore.entity.Station;
import com.dart.explore.entity.StationColor;
import com.dart.explore.service.ImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ImportService.class)
public class StationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ImportService importService;

    @Test
    public void whenFindByColor_thenReturnStations() {
        // given
        Station testStation = new Station();
        testStation.setName("This is a test station");
        testStation.getColor().add(StationColor.GREEN);
        entityManager.persist(testStation);
        entityManager.flush();

        // when
        List<Station> found = stationRepository.findStationByColor(StationColor.GREEN);

        // then
        // tests that both the return and the add worked since the count is 18 + 1 we added
        assertThat(found).hasSize(19);
        // tests that we can data is coming back correctly since we can correctly match the name
        assertThat(found.get(found.size() - 1).getName()).isEqualTo(testStation.getName());
    }
}
