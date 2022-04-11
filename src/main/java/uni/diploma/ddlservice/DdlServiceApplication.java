package uni.diploma.ddlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import uni.diploma.ddlservice.entities.Session;

import java.util.concurrent.ConcurrentLinkedQueue;

@ServletComponentScan
@SpringBootApplication
public class DdlServiceApplication {

    public static final ConcurrentLinkedQueue<Session> webSessions = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        SpringApplication.run(DdlServiceApplication.class, args);
    }

}
