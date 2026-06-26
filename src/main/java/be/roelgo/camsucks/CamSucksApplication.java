package be.roelgo.camsucks;

import be.roelgo.camsucks.application.ShellCommands;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.shell.core.command.annotation.EnableCommand;

@SpringBootApplication
@Import(CamSucksConfiguration.class)
@EnableCommand({ ShellCommands.class })
public class CamSucksApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamSucksApplication.class, args);
    }
}
