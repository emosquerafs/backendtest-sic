package co.gov.sic.testsic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "file:${spring.config.location}/application.yaml")
public class TestSicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSicApplication.class, args);
    }

}
