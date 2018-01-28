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
@PropertySource({"classpath:application.properties"})
@ConfigurationProperties(prefix = "camsucks", ignoreInvalidFields = true)
public class PropertyService {

    private final Log logger = LogFactory.getLog(getClass());

    private Integer cpuMax;

    public Integer getCpuMax() {
        return cpuMax;
    }

    public void setCpuMax(Integer cpuMax) {
        this.cpuMax = cpuMax;
    }

    @PostConstruct
    public void init() {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(this, SHORT_PREFIX_STYLE);
        logger.debug(builder.setExcludeFieldNames("logger").toString());
    }

}
