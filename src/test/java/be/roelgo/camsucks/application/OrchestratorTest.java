package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrchestratorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SensorService sensorService;
    @Mock
    private Regulator regulator;
    @Mock
    private FanService fanService;


    @InjectMocks
    private Orchestrator orchestrator;

    @Test
    public void orchestratorShould_pollRegulateAndSend() {
        Speed speed = new Speed(40.0);
        SensorData sensorData = new SensorData(50.0, 60.0);
        when(sensorService.poll()).thenReturn(sensorData);
        when(regulator.regulate(sensorData)).thenReturn(speed);

        orchestrator.orchestrate();

        verify(sensorService).poll();
        verify(regulator).regulate(sensorData);
        verify(fanService).sendSpeed(speed);
    }
}