package pl.lets_eat_together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"pl.lets_eat_together.view"})
@ComponentScan({"pl.lets_eat_together"})
public class LetsEatTogether {

    public static void main(String[] args) {
        SpringApplication.run(LetsEatTogether.class, args);

    }

}
