package be.roelgo.camsucks.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Orchestrator {

    @Scheduled(fixedRate = 5000)
    public void orchestrate() {
        pollSensors();
        regulateSpeed();
        sendSpeed();
    }

    private void pollSensors() {
        System.out.println("Polling sensors.");
    }

    private void regulateSpeed() {
        System.out.println("Regulating speed.");
    }

    private void sendSpeed() {
        System.out.println("Sending speed.");
    }
}
