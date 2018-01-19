package be.roelgo.camsucks;

import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.common.collect.ImmutableBiMap.of;

@Configuration
public class IntegrationTestConfiguration {
    @Inject
    private ResourceLoader resourceLoader;

    @Bean
    SensorProvider sensorProvider() {

        Resource resource = resourceLoader.getResource("classpath:testScenario.txt");

        StringBuilder stringBuilder = new StringBuilder();

        try {
            Files.readAllLines(Paths.get(resource.getURI())).forEach(line -> stringBuilder.append(line).append("\r\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read file :(", e);
        }
        return new SensorProvider() {
            private Components components = JSensors.get.config(of(
                    "debugMode", "true",
                    "testMode", "STUB",
                    "stubContent", stringBuilder.toString().trim())).components();

            @Override
            public Components getComponents() {
                return components;
            }
        };

    }
}
