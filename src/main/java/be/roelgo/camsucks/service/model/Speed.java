package be.roelgo.camsucks.service.model;

public class Speed {

    private Double percentage;

    public Speed(Double percentage) {
        this.percentage = percentage;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Speed{" +
                "percentage=" + percentage +
                '}';
    }
}
