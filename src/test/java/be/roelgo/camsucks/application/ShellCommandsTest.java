package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.shell.table.Table;

import static be.roelgo.camsucks.utils.TableUtils.buildTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShellCommandsTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private ShellCommands shellCommands;

    @Test
    public void poll_shouldCallSensorService() {
        SensorData sensorData = new SensorData(50.0, 60.0);
        when(sensorService.poll()).thenReturn(sensorData);
        Table expected = buildTable(sensorData);

        Table actual = shellCommands.poll();

        verify(sensorService).poll();
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}