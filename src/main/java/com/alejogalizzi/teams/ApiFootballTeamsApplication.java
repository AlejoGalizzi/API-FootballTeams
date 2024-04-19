package com.alejogalizzi.teams;

import com.alejogalizzi.teams.security.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@Import(WebSecurityConfig.class)
public class ApiFootballTeamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFootballTeamsApplication.class, args);
	}

}
