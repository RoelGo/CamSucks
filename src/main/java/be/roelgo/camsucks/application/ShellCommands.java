package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorService;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;


@SuppressWarnings("UnusedReturnValue")
@Component
public class ShellCommands {

    private final SensorService sensorService;

    public ShellCommands(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Command("Poll sensors and Display result")
    public String poll() {
        return "poll";
    }

}
