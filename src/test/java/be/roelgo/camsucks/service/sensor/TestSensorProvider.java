package be.roelgo.camsucks.service.sensor;

import be.roelgo.camsucks.service.model.ComponentTemperatures;
import be.roelgo.camsucks.service.model.SensorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Test {@link SensorProvider} that derives {@link ComponentTemperatures} from a stub text file
 * (e.g. {@code testScenario.txt}) instead of reading real hardware.
 *
 * <p>Previously this delegated to jSensors' STUB mode, but {@code SensorProvider} no longer
 * depends on jSensors (it now returns {@link ComponentTemperatures}), so the stub is parsed
 * directly here. The parsing mirrors the production extraction logic:
 * <ul>
 *     <li>CPU value: the CPU section's "Temp CPU Package" (or "Temp CPU Total")</li>
 *     <li>MOBO value: the MOBO section's CPU-related temperature ("Temp CPU Core")</li>
 *     <li>GPU value: the GPU section's first temperature ("Temp GPU Core")</li>
 * </ul>
 */
@Component
@Profile({"test"})
public class TestSensorProvider implements SensorProvider {

    private static final String COMPONENT_MARKER = "[COMPONENT]";

    private String stubContent = "";

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public ComponentTemperatures getComponents() {
        String section = "";
        Double cpuValue = 0.0;
        Double moboValue = 0.0;
        Double gpuValue = 0.0;

        for (String rawLine : stubContent.split("\\R")) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.equals(COMPONENT_MARKER)) {
                section = "";
                continue;
            }

            // The line immediately following a [COMPONENT] marker is the section name.
            if (section.isEmpty() && !line.contains(":")) {
                section = line.toUpperCase();
                continue;
            }

            String lower = line.toLowerCase();
            switch (section) {
                case "CPU" -> {
                    if (lower.startsWith("temp") && (lower.contains("package") || lower.contains("total"))) {
                        cpuValue = parseValue(line);
                    }
                }
                case "MOBO" -> {
                    if (lower.startsWith("temp") && lower.contains("cpu")) {
                        moboValue = parseValue(line);
                    }
                }
                case "GPU" -> {
                    if (lower.startsWith("temp") && lower.contains("gpu") && gpuValue == 0.0) {
                        gpuValue = parseValue(line);
                    }
                }
                default -> {
                    // ignore other sections (DISK, etc.)
                }
            }
        }

        return new ComponentTemperatures(cpuValue, moboValue, gpuValue);
    }

    private Double parseValue(String line) {
        int colon = line.lastIndexOf(':');
        if (colon < 0 || colon == line.length() - 1) {
            return 0.0;
        }
        try {
            return Double.parseDouble(line.substring(colon + 1).trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setStubString(String stubFile) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + stubFile);

        List<String> lines = Files.readAllLines(Paths.get(resource.getURI()));
        this.stubContent = String.join("\n", lines).trim();
    }
}
