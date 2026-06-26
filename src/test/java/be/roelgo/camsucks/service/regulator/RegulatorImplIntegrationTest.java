package be.roelgo.camsucks.service.regulator;

import be.roelgo.camsucks.CamSucksApplication;
import be.roelgo.camsucks.service.model.Regulator;
import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.sensor.TestSensorProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CamSucksApplication.class})
@ActiveProfiles({"test"})
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class RegulatorImplIntegrationTest {

    @Autowired
    private TestSensorProvider testSensorProvider;

    @Autowired
    private RegulatorImpl regulator;

    @Test
    public void regulate() throws Exception {
        testSensorProvider.setStubString("testScenario.txt");

        regulator.regulate(new SensorData(50.0, 50.0));
        regulator.regulate(new SensorData(80.0, 50.0));
        regulator.regulate(new SensorData(90.0, 50.0));
        regulator.regulate(new SensorData(70.0, 50.0));

    }
}