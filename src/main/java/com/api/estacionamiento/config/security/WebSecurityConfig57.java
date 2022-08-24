package com.api.estacionamiento.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig57 {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //configuracion para version 5.7 de spring security
    //ahora es un bean, otra manera de declarar el acceso es en cotroller, en cada handler
    //lo anterior se hace con la anotacion @EnableGlobalMethodSecurity(prePostEnabled = true)
    //para controlar el acceso en cada handler se usa la anotacion  @PreAuthorize("hasRole('ROLE_ADMIN')")
    http
            .httpBasic()
            .and()
            .authorizeHttpRequests()
            //.antMatchers(HttpMethod.GET, "/parking-spot/**").permitAll()
            //.antMatchers(HttpMethod.POST, "/parking-spot").hasRole("USER")
            //.antMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    return http.build();
  }

  //Metodo para encriptar password
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
