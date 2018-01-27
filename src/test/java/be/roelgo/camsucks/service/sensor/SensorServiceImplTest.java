package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.CamSucksApplication;
import be.roelgo.camsucks.IntegrationTestConfiguration;
import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        CamSucksApplication.class,
        IntegrationTestConfiguration.class
})
@ActiveProfiles({"test"})
public class SensorServiceImplTest {

    @Inject
    private SensorServiceImpl sensorService;

    @Test
    public void poll() {
        SensorData actual = sensorService.poll();

        assertThat(actual).isNotNull();
        assertThat(actual.getCpu()).isEqualTo(46.5);
        assertThat(actual.getGpu()).isEqualTo(29.0);
    }
}
