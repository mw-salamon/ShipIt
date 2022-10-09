package pl.lets_eat_together;

import com.vaadin.flow.component.dependency.NpmPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@ComponentScan({"pl.lets_eat_together.view"})
@ComponentScan({"pl.lets_eat_together"})
@EnableJpaRepositories(basePackages={"pl.lets_eat_together.repository","pl.lets_eat_together.registration.token","pl" +
        ".lets_eat_together.user"})
@EnableTransactionManagement
public class LetsEatTogether extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LetsEatTogether.class, args);

    }
}
