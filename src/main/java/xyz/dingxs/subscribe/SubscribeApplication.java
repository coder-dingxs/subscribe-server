package xyz.dingxs.subscribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubscribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscribeApplication.class, args);
    }

}
