package pl.lets_eat_together;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"pl.lets_eat_together.view"})
@ComponentScan({"pl.lets_eat_together"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages={"pl.lets_eat_together.repository","pl.lets_eat_together.registration.token","pl" +
        ".lets_eat_together.user"})
@EnableTransactionManagement
public class LetsEatTogether {

    public static void main(String[] args) {
        SpringApplication.run(LetsEatTogether.class, args);

    }

}
