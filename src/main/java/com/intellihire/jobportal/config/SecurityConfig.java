package com.intellihire.jobportal.config;

import com.intellihire.jobportal.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/home", "/jobs", "/jobs/**", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/jobs", "/api/jobs/**").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/logout").permitAll()
                .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/employee/**", "/api/employee/**").hasRole("EMPLOYEE")
                .requestMatchers("/candidate/**", "/api/candidate/**").hasRole("CANDIDATE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    // Return 401 for API requests instead of redirecting
                    if (request.getRequestURI().startsWith("/api")) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    } else {
                        response.sendRedirect("/login");
                    }
                }))
            .httpBasic(withDefaults()) // Enable Basic Auth for tools like Postman
            .userDetailsService(userDetailsService);

        return http.build();
    }
}
