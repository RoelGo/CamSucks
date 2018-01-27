package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SensorProviderImpl implements SensorProvider {
    @Override
    public Components getComponents() {
        return JSensors.get.components();
    }
}
