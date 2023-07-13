package tn.esprit.pi_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.esprit.pi_backend.restControllers.SmsController;

@SpringBootApplication
public class PiBackendApplication {

    public static void main(String[] args) {
       // SpringApplication.run(SmsController.class, args);
        SpringApplication.run(PiBackendApplication.class, args);
    }

}
