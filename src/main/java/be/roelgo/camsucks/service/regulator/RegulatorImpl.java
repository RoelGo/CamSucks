package be.roelgo.camsucks.service.regulator;

import be.roelgo.camsucks.service.PropertyService;
import be.roelgo.camsucks.service.model.Regulator;
import be.roelgo.camsucks.service.model.SensorData;
import be.roelgo.camsucks.service.model.Speed;
import com.stormbots.MiniPID;
import org.springframework.stereotype.Component;

@Component
public class RegulatorImpl implements Regulator {

    private PropertyService propertyService;
    private MiniPID cpuPID;
    private MiniPID gpuPID;


    public RegulatorImpl(PropertyService propertyService, MiniPID cpuPID, MiniPID gpuPID) {
        this.propertyService = propertyService;
        this.cpuPID = cpuPID;
        this.gpuPID = gpuPID;
        setupPIDs();
    }

    private void setupPIDs() {
        cpuPID.setDirection(true);
        gpuPID.setDirection(true);

        cpuPID.setP(propertyService.getCpuP());
        cpuPID.setI(propertyService.getCpuI());
        cpuPID.setD(propertyService.getCpuD());

        gpuPID.setP(propertyService.getGpuP());
        gpuPID.setI(propertyService.getGpuI());
        gpuPID.setD(propertyService.getGpuD());

        updatePIDs();
    }

    private void updatePIDs() {
        cpuPID.setOutputLimits(propertyService.getMinSpeed() - propertyService.getPrefSpeed(), 100.0 - propertyService.getPrefSpeed());
        gpuPID.setOutputLimits(propertyService.getMinSpeed() - propertyService.getPrefSpeed(), 100.0 - propertyService.getPrefSpeed());
    }

    @Override
    public Speed regulate(SensorData sensorData) {
        Speed cpuSpeed = new Speed(cpuPID.getOutput(sensorData.getCpu(), propertyService.getCpuMax()) + propertyService.getPrefSpeed());
        Speed gpuSpeed = new Speed(gpuPID.getOutput(sensorData.getGpu(), propertyService.getGpuMax()) + propertyService.getPrefSpeed());
        return cpuSpeed.getPercentage() > gpuSpeed.getPercentage() ? cpuSpeed : gpuSpeed;
    }
}
