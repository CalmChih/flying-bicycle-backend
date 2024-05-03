package top.fallingintodreams.flying.bicycle.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author CalmChih
 */
@SpringBootApplication
@EnableScheduling
public class FlyingBicycleBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyingBicycleBackendApplication.class, args);
    }

}
