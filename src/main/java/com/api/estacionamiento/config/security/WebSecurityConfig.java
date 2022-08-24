package com.api.estacionamiento.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  final UserDetailsServiceImpl userDetailsService;

  public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
            .httpBasic()
            .and()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/parking-spot/**").permitAll()
            .antMatchers(HttpMethod.POST, "/parking-spot").hasRole("USER")
            .antMatchers(HttpMethod.DELETE, "/parking-spot/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    //permitAll(), hace que se pueda acceder a los services
    //.authenticated(), bloquea el acceso al controller, devuelve 401
    //csrf().disable(), permite utilizar los method:PUT,DELETE, POST
    //.antMatchers(metodoHTTP, Path.rol de usuario
    //.hasAnyRole(), sirve para otorgar permiso a mas de un rol ej: "USER","ADMIN"
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

            /*inMemoryAuthentication()
            .withUser("alfredo")
            .password(passwordEncoder().encode("147852"))
            .roles("ADMIN");*/
    //springSecurity por obligacion pide un passwordEncoder, no acepta cualquier password
    //este metodo guarda al usuario en memoria, es un metodo de WebSecurityConfigurerAdapter
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
    //Esta metodo generea un passwordEncoder o cubre el password con otro más largo y seguro
  }

}
