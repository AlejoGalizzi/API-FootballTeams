package com.alejogalizzi.teams.security.config;

import com.alejogalizzi.teams.security.jwt.JwtAuthenticationEntryPoint;
import com.alejogalizzi.teams.security.jwt.JwtRequestFilter;
import com.alejogalizzi.teams.service.implementations.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "basicAuth",
    scheme = "basic")
public class WebSecurityConfig {

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new JwtAuthenticationEntryPoint();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
      BCryptPasswordEncoder bCryptPasswordEncoder)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(jwtUserDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(requests ->
            requests.requestMatchers("/auth/login", "/auth/register",
                    "/swagger-ui/**","/v3/api-docs/**",
                    "/swagger-resources/*").permitAll()
            .requestMatchers("/equipos/**").authenticated())
        .httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler()));
    return http.build();
  }
}
