package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.SensorProvider;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.common.collect.ImmutableBiMap.of;

@Component
@Profile({"test"})
public class TestSensorProvider implements SensorProvider {

    private String stubString = "";
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public Components getComponents() {
        return JSensors.get.config(of(
                "debugMode", "true",
                "testMode", "STUB",
                "stubContent", getStubString())).components();
    }

    private String getStubString() {
        return stubString;
    }

    public void setStubString(String stubFile) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + stubFile);

        StringBuilder stringBuilder = new StringBuilder();

        Files.readAllLines(Paths.get(resource.getURI())).forEach(line -> stringBuilder.append(line).append("\r\n"));

        this.stubString = stringBuilder.toString().trim();
    }
}
