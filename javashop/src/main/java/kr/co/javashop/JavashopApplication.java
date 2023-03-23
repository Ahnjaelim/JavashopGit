package kr.co.javashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavashopApplication.class, args);
	}

}
