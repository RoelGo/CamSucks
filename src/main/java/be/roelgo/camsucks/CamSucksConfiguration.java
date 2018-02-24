package be.roelgo.camsucks;

import be.roelgo.camsucks.service.PropertyService;
import com.stormbots.MiniPID;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(PropertyService.class)
public class CamSucksConfiguration {

    @Bean
    MiniPID cpuPID() {
        return new MiniPID(1, 0, 0);
    }

    @Bean
    MiniPID gpuPID() {
        return new MiniPID(1, 0, 0);
    }

}
