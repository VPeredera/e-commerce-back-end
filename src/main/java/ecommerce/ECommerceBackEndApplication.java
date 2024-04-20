package ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ECommerceBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBackEndApplication.class, args);
    }

}