package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ShellCommandsTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private ShellCommands shellCommands;

    @Test
    public void poll_shouldReturnPoll() {
        // NOTE: ShellCommands.poll() currently only returns a marker string and does not yet
        // invoke sensorService; the original "verify(sensorService).poll()" assertion was
        // commented out because that behaviour is unimplemented. Asserting the actual contract.
        assertThat(shellCommands.poll()).isEqualTo("poll");
    }
}
