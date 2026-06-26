package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.ComponentTemperatures;
import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorProvider;
import be.roelgo.camsucks.service.model.SensorService;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import org.springframework.stereotype.Component;

import static java.lang.Double.valueOf;

@Component
public class SensorServiceImpl implements SensorService {

    private final SensorProvider sensorProvider;

    public SensorServiceImpl(SensorProvider sensorProvider) {
        this.sensorProvider = sensorProvider;
    }

    @Override
    public SensorData poll() {
        ComponentTemperatures components = sensorProvider.getComponents();

        SensorData sensorData = new SensorData();

        Double cpuValue = components.cpuValue();
        Double moboValue = components.moboValue();
        Double gpuValue = components.gpuValue();

        sensorData.setCpu(valueOf(0.0).equals(cpuValue) ? moboValue : cpuValue);
        sensorData.setGpu(gpuValue);

        return sensorData;
    }
}
