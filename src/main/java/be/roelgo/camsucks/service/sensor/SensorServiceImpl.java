package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;
import org.springframework.stereotype.Component;

@Component
public class SensorServiceImpl implements SensorService {
    @Override
    public SensorData poll() {
        return null;
    }
}
