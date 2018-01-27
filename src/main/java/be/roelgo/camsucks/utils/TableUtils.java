package be.roelgo.camsucks.utils;

import be.roelgo.camsucks.service.model.SensorData;
import org.springframework.shell.table.*;

public class TableUtils {


    public static Table buildTable(SensorData sensorData) {
        return buildTable(buildTableModel(sensorData));
    }

    public static Table buildTable(TableModel tableModel) {
        return new TableBuilder(tableModel)
                .addFullBorder(BorderStyle.fancy_light_double_dash)
                .build();
    }

    public static TableModel buildTableModel(SensorData sensorData) {
        return new TableModelBuilder<String>()
                .addRow()
                .addValue("Name")
                .addValue("Value")
                .addRow()
                .addValue("CPU")
                .addValue(sensorData.getCpu().toString())
                .addRow()
                .addValue("GPU")
                .addValue(sensorData.getGpu().toString())
                .build();
    }

}
