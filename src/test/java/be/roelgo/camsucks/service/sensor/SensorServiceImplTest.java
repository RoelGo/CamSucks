package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

public class SensorServiceImplTest {

    private SensorService sensorService;

    @Before
    public void setUp() {
        sensorService = new SensorServiceImpl();
    }

    @Test
    public void poll() {
        SensorData actual = sensorService.poll();

        assertThat(actual).isNotNull();
        assertThat(actual.getCpu()).isNotNull();
        assertThat(actual.getGpu()).isNotNull();

        System.out.println(actual);
    }
}
