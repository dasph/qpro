package me.amvse.qpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QproApplication {
	public static void main (String[] args) {
		SpringApplication.run(QproApplication.class, args);
	}
}
