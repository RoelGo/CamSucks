package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorProvider;
import be.roelgo.camsucks.service.model.SensorService;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.stereotype.Component;

@Component
public class SensorServiceImpl implements SensorService {

    private final SensorProvider sensorProvider;
    private Components components;

    public SensorServiceImpl(SensorProvider sensorProvider) {
        this.sensorProvider = sensorProvider;
        this.components = sensorProvider.getComponents();
    }

    @Override
    public SensorData poll() {
        SensorData sensorData = new SensorData();

        sensorData.setCpu(
                components.cpus.stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Not sure what is wrong here :("))
                        .sensors.temperatures.stream()
                        .filter(temperature -> temperature.name.toLowerCase().contains("total") ||
                                temperature.name.toLowerCase().contains("package"))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Not sure what is wrong here :("))
                        .value);

        sensorData.setGpu(
                components.gpus.stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Not sure what is wrong here :("))
                        .sensors.temperatures.stream()
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Not sure what is wrong here :("))
                        .value);

        return sensorData;
    }
}
