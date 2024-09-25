package com.SCRUM.APP.config;

import com.SCRUM.APP.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final AuthTokenFilter authTokenFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AuthTokenFilter authTokenFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authTokenFilter = authTokenFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/v1/tasks/create").hasAuthority("MANAGER")
                                .requestMatchers("/api/v1/tasks/all").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/tasks/task/{id}").permitAll()
                                .requestMatchers("/api/v1/tasks/completed").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/tasks/not_completed").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/tasks/update/{id}").permitAll()
                                .requestMatchers("/api/v1/tasks/delete/{id}").hasAuthority("MANAGER")
                                .requestMatchers("/api/v1/tasks/delete/all").hasAuthority("MANAGER")
                                .requestMatchers("/api/v1/projects/list").permitAll()
                                .requestMatchers("/api/v1/projects/list/{id}").permitAll()
                                .requestMatchers("/api/v1/projects/list/completed/{completed}").permitAll()
                                .requestMatchers("/api/v1/projects/create").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/projects/update/{id}").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/projects/delete/{id}").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/tasks/create").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/tasks/delete/{id}").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/tasks/update/{id}").hasAnyAuthority("ADMIN","MANAGER")
                                .requestMatchers("/api/v1/users").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/create").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/list/{id}").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/list").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/update/{id}").permitAll()
                                .requestMatchers("/api/v1/users/delete/{id}").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
