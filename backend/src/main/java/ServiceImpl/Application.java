package ServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.*;

@SpringBootApplication
@EnableAutoConfiguration // Sprint Boot Auto Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Object.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources();
    }
}
