package be.roelgo.camsucks.service.regulator;

import be.roelgo.camsucks.service.model.Regulator;
import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.Speed;
import org.springframework.stereotype.Component;

@Component
public class RegulatorImpl implements Regulator {
    @Override
    public Speed regulate(SensorData sensorData) {
        return new Speed();
    }
}
