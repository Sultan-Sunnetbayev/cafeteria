package tm.salam.cafeteria3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Cafeteria3Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context=SpringApplication.run(Cafeteria3Application.class, args);
        PasswordEncoder passwordEncoder=context.getBean(PasswordEncoder.class);
        System.out.println(passwordEncoder.encode("admin"));
    }

}
