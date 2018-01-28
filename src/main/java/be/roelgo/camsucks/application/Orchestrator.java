package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.*;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Orchestrator {

    private final SensorService sensorService;
    private final Regulator regulator;
    private final FanService fanService;

    public Orchestrator(SensorService sensorService, Regulator regulator, FanService fanService) {
        this.sensorService = sensorService;
        this.regulator = regulator;
        this.fanService = fanService;
    }

    @Scheduled(fixedRate = 5000)
    public void orchestrate() {
        sendSpeed(regulateSpeed(pollSensors()));
    }

    private SensorData pollSensors() {
        return sensorService.poll();
    }

    private Speed regulateSpeed(SensorData sensorData) {
        return regulator.regulate(sensorData);
    }

    private void sendSpeed(Speed speed) {
        fanService.sendSpeed(speed);
    }
}
