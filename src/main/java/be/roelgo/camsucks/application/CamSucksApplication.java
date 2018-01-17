package be.roelgo.camsucks.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CamSucksApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamSucksApplication.class, args);
    }
}
