package be.roelgo.camsucks.utils;

import be.roelgo.camsucks.service.model.SensorData;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.shell.table.TableModelBuilder;

import static org.springframework.shell.table.BorderStyle.fancy_heavy;

public class TableUtils {


    public static Table buildTable(SensorData sensorData) {
        return buildTable(buildTableModel(sensorData));
    }

    public static Table buildTable(TableModel tableModel) {
        return new TableBuilder(tableModel)
                .addFullBorder(fancy_heavy)
                .build();
    }

    public static TableModel buildTableModel(SensorData sensorData) {
        return new TableModelBuilder<String>()
                .addRow()
                .addValue("CPU")
                .addValue(temperature(sensorData.getCpu()))
                .addRow()
                .addValue("GPU")
                .addValue(temperature(sensorData.getGpu()))
                .build();
    }

    private static String temperature(Double temperature) {
        return temperature + "°C" ;
    }
}
