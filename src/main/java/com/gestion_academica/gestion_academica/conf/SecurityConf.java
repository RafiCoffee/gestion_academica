package com.gestion_academica.gestion_academica.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConf {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("SELECT username, password, 1 AS enabled FROM usuario WHERE username = ?")
        .authoritiesByUsernameQuery("SELECT username, authority FROM usuario WHERE username = ?");
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests((requests) -> requests
        .requestMatchers("/img/**", "/js/**", "/register/**", "/ayuda/**", "/login").permitAll()
        .requestMatchers("/alumno/add", "/profesor/add", "/asignatura/add").hasAuthority("GESTOR")
        .anyRequest().permitAll()).formLogin((formLogin) -> formLogin.permitAll())
        .rememberMe(Customizer.withDefaults()).logout((logout) -> logout.invalidateHttpSession(true)
        .logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll()).build();
    }
}
