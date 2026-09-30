package com.ResortManagementBE.RRMS;

import com.ResortManagementBE.RRMS.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class RrmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RrmsApplication.class, args);
	}

}
