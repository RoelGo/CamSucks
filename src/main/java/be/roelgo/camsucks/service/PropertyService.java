package be.roelgo.camsucks.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Component
@ConfigurationProperties(prefix = "camsucks", ignoreInvalidFields = true)
public class PropertyService {

    private final Log logger = LogFactory.getLog(getClass());

    private Double cpuMax = 70.0;
    private Double gpuMax = 70.0;
    private Double prefSpeed = 50.0;
    private Double minSpeed = 40.0;
    private Double cpuP = 1.0;
    private Double cpuI = 0.0;
    private Double cpuD = 0.0;
    private Double gpuP = 1.0;
    private Double gpuI = 0.0;
    private Double gpuD = 0.0;

    @PostConstruct
    public void init() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, SHORT_PREFIX_STYLE);
        logger.info(builder.setExcludeFieldNames("logger").toString());
    }

    public double getCpuMax() {
        return cpuMax;
    }

    public void setCpuMax(Double cpuMax) {
        this.cpuMax = cpuMax;
    }

    public double getPrefSpeed() {
        return prefSpeed;
    }

    public void setPrefSpeed(Double prefSpeed) {
        this.prefSpeed = prefSpeed;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(Double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public double getGpuMax() {
        return gpuMax;
    }

    public void setGpuMax(Double gpuMax) {
        this.gpuMax = gpuMax;
    }

    public double getCpuP() {
        return cpuP;
    }

    public void setCpuP(Double cpuP) {
        this.cpuP = cpuP;
    }

    public double getCpuI() {
        return cpuI;
    }

    public void setCpuI(Double cpuI) {
        this.cpuI = cpuI;
    }

    public double getCpuD() {
        return cpuD;
    }

    public void setCpuD(Double cpuD) {
        this.cpuD = cpuD;
    }

    public double getGpuP() {
        return gpuP;
    }

    public void setGpuP(Double gpuP) {
        this.gpuP = gpuP;
    }

    public double getGpuI() {
        return gpuI;
    }

    public void setGpuI(Double gpuI) {
        this.gpuI = gpuI;
    }

    public double getGpuD() {
        return gpuD;
    }

    public void setGpuD(Double gpuD) {
        this.gpuD = gpuD;
    }
}
