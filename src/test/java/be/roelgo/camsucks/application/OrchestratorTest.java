package be.roelgo.camsucks.application;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class OrchestratorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private Orchestrator orchestrator;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void name() {

        orchestrator.orchestrate();

        String actual = outContent.toString();

        assertThat(actual).contains("Polling sensors.");
        assertThat(actual).contains("Regulating speed.");
        assertThat(actual).contains("Sending speed.");
    }
}