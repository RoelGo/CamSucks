package be.roelgo.camsucks.service.regulator;


import be.roelgo.camsucks.service.PropertyService;
import be.roelgo.camsucks.service.model.SensorData;
import com.stormbots.MiniPID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Lenient strictness: @InjectMocks injects a single MiniPID mock (by type) into both the
// cpuPID and gpuPID constructor parameters, so the cpuPID stub below is never exercised on
// the injected instance. The JUnit 4 MockitoRule used to tolerate this; under JUnit 5's strict
// MockitoExtension we opt into lenient stubbing to preserve the original test's intent
// (verifying that the regulator reads its limits from PropertyService).
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegulatorImplTest {

    @Mock
    private PropertyService propertyService;

    //Mockito let me down here and injects only one PID in both PID fields in the UUT
    @Mock(name = "cpuPID")
    private MiniPID cpuPID;

    @Mock(name = "gpuPID")
    private MiniPID gpuPID;

    @InjectMocks
    private RegulatorImpl regulator;

    @Test
    public void regulateGetsPropertiesFromPropertyService() {
        SensorData sensorData = new SensorData(50.0, 50.0);
        when(propertyService.getCpuMax()).thenReturn(80.0);
        when(propertyService.getGpuMax()).thenReturn(80.0);
        when(cpuPID.getOutput(sensorData.getCpu(), 80)).thenReturn(5.0);
        when(gpuPID.getOutput(sensorData.getGpu(), 80)).thenReturn(5.0);

        regulator.regulate(sensorData);

//        verify(cpuPID).getOutput(sensorData.getCpu(), 80);
//        verify(gpuPID).getOutput(sensorData.getGpu(), 80);
        verify(propertyService).getCpuMax();
        verify(propertyService).getGpuMax();
    }
}