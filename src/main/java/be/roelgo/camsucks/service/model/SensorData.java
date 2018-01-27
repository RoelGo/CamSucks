package be.roelgo.camsucks.service.model;

public class SensorData {
    private Double cpu;
    private Double gpu;

    public SensorData() {
        cpu = 0.0;
        gpu = 0.0;
    }

    public SensorData(Double cpu, Double gpu) {
        this.cpu = cpu;
        this.gpu = gpu;
    }

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Double getGpu() {
        return gpu;
    }

    public void setGpu(Double gpu) {
        this.gpu = gpu;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "cpu=" + cpu +
                ", gpu=" + gpu +
                '}';
    }
}
