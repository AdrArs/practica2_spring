package com.codigo.msariasantaurco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.codigo")
@EnableJpaRepositories("com.codigo")
@EntityScan("com.codigo.*")
@EnableFeignClients("com.codigo.*")
public class MsAriasAntaurcoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAriasAntaurcoApplication.class, args);
	}
}
