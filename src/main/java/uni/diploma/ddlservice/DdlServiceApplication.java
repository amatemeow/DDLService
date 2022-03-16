package uni.diploma.ddlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class DdlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdlServiceApplication.class, args);
    }

}
