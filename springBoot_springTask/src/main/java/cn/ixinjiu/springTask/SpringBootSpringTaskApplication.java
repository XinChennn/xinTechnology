package cn.ixinjiu.springTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootSpringTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSpringTaskApplication.class, args);
    }

}
