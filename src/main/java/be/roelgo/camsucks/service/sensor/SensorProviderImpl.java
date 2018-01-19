package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.google.common.collect.ImmutableBiMap.of;

@Component
@Profile("!test")
public class SensorProviderImpl implements SensorProvider {
    private Components components = JSensors.get.components();

    @Override
    public Components getComponents() {
        return components;
    }
}
