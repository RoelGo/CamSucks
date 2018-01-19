package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.SensorService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrchestratorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private Orchestrator orchestrator;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void orchestratorShould_pollRegulateAndSend() {

        when(sensorService.poll()).thenReturn(new SensorData());

        orchestrator.orchestrate();

        String actual = outContent.toString();

        verify(sensorService).poll();
        assertThat(actual).contains("Regulating speed.");
        assertThat(actual).contains("Sending speed.");
    }
}