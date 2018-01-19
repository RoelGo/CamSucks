package be.roelgo.camsucks.application;

import be.roelgo.camsucks.service.model.SensorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Orchestrator {

    private final SensorService sensorService;

    public Orchestrator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Scheduled(fixedRate = 5000)
    public void orchestrate() {
        pollSensors();
        regulateSpeed();
        sendSpeed();
    }

    private void pollSensors() {
        sensorService.poll();
    }

    private void regulateSpeed() {
        System.out.println("Regulating speed.");
    }

    private void sendSpeed() {
        System.out.println("Sending speed.");
    }
}
