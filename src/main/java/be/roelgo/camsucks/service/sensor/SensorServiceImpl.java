package be.roelgo.camsucks.service.sensor;

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
    private Components components;

    public SensorServiceImpl(SensorProvider sensorProvider) {
        this.sensorProvider = sensorProvider;
        this.components = sensorProvider.getComponents();
    }

    @Override
    public SensorData poll() {
        SensorData sensorData = new SensorData();

        Double cpuValue = getCPUValue();
        Double moboValue = getMOBOValue();
        Double gpuValue = getGPUValue();

        sensorData.setCpu(valueOf(0.0).equals(cpuValue) ? moboValue : cpuValue);
        sensorData.setGpu(gpuValue);

        return sensorData;
    }

    private Double getGPUValue() {
        return components.gpus.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find GPU :("))
                .sensors.temperatures.stream()
                .findFirst()
                .orElseGet(() -> new Temperature("Couldn't find GPU temperature sensor :(", 0.0))
                .value;
    }

    private Double getCPUValue() {
        return components.cpus.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find CPU :("))
                .sensors.temperatures.stream()
                .filter(temperature -> temperature.name.toLowerCase().contains("total") ||
                        temperature.name.toLowerCase().contains("package"))
                .findFirst()
                .orElseGet(() -> new Temperature("Couldn't find CPU package sensor :(", 0.0))
                .value;
    }

    private Double getMOBOValue() {
        return components.mobos.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find MOBO :("))
                .sensors.temperatures.stream()
                .filter(temperature -> temperature.name.toLowerCase().contains("cpu"))
                .findFirst()
                .orElseGet(() -> new Temperature("Couldn't find CPU sensor :(", 0.0))
                .value;
    }
}
