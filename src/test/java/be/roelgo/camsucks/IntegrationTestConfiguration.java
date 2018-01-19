package be.roelgo.camsucks;

import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.ImmutableBiMap.of;

@Configuration
public class IntegrationTestConfiguration {
    @Bean
    SensorProvider sensorProvider() {
        return new SensorProvider() {
            private Components components = JSensors.get.config(of("debugMode", "true","stubFile","testScenario")).components();

            @Override
            public Components getComponents() {
                return components;
            }
        };
    }
}
