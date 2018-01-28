package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;

import static be.roelgo.camsucks.utils.TableUtils.buildTable;

@SuppressWarnings("UnusedReturnValue")
@ShellComponent
public class ShellCommands {

    private final SensorService sensorService;

    public ShellCommands(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @ShellMethod("Poll sensors and Display result")
    public Table poll() {
        return buildTable(sensorService.poll());
    }

}
