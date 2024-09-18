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
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/test/all").permitAll()
                                .requestMatchers("/api/test/user").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/test/admin").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/tasks/create").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/create").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/all").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/users/update/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/projects/create").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/projects/list").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/post/getAll").permitAll()
                                .requestMatchers("/api/v1/donations").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/v1/donations/delete/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/donations/update/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/donations/getAll").permitAll()
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
