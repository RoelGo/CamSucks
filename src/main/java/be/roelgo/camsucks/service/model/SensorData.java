package be.roelgo.camsucks.service.model;

public class SensorData {
    private Double cpu;
    private Double gpu;

    public Double getCpu() {
        return cpu;
    }

    public Double getGpu() {
        return gpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
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
