package be.roelgo.camsucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CamSucksConfiguration.class)
public class CamSucksApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamSucksApplication.class, args);
    }
}
