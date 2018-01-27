package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.CamSucksApplication;
import be.roelgo.camsucks.service.model.SensorData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        CamSucksApplication.class
})
@ActiveProfiles({"test"})
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class SensorServiceImplTest {

    @Inject
    private TestSensorProvider testSensorProvider;

    @Inject
    private SensorServiceImpl sensorService;

    @Test
    public void poll() throws Exception {

        testSensorProvider.setStubString("testScenario.txt");

        SensorData actual = sensorService.poll();

        assertThat(actual).isNotNull();
        assertThat(actual.getCpu()).isEqualTo(46.5);
        assertThat(actual.getGpu()).isEqualTo(29.0);
    }
}
