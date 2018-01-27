package be.roelgo.camsucks.service.model;

public interface Regulator {
    Speed regulate(SensorData sensorData);
}
