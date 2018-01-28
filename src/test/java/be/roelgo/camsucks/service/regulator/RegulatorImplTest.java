package be.roelgo.camsucks.service.regulator;


import be.roelgo.camsucks.service.PropertyService;
import be.roelgo.camsucks.service.model.SensorData;
import com.stormbots.MiniPID;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegulatorImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private PropertyService propertyService;

    @Mock
    private MiniPID cpuPID;

    @Mock
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

        verify(propertyService).getCpuMax();
        verify(propertyService).getGpuMax();
    }
}