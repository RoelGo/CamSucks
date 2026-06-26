package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.ComponentTemperatures;
import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public class JsensorSensorProviderImpl implements SensorProvider {
    @Override
    public ComponentTemperatures getComponents() {
        Components components = JSensors.get.components();
        return new ComponentTemperatures(
                getCPUValue(components),
                getMOBOValue(components),
                getGPUValue(components)
        );
    }

    private Double getGPUValue(Components components) {
        return components.gpus.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find GPU :("))
                .sensors.temperatures.stream()
                .findFirst()
                .orElseGet(() -> new Temperature("Couldn't find GPU temperature sensor :(", 0.0))
                .value;
    }

    private Double getCPUValue(Components components) {
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

    private Double getMOBOValue(Components components) {
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
