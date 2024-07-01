package com.gr1.springboot.mvc.studentmanagement.security;

import com.gr1.springboot.mvc.studentmanagement.dao.AccountRepository;
import com.gr1.springboot.mvc.studentmanagement.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/api/students").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/api/admin/students").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/admin/courses").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/admin/students/count").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/admin/courses/count").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/students/**").hasRole("USER")
                                .requestMatchers("/update-student/**").hasRole("ADMIN")
                                .requestMatchers("/update-course/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/admin/students").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/admin/courses").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/admin/students/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/admin/courses/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/admin/students/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/admin/courses/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/registration/registered").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/registration/register").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/registration/unregister").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/api/registration/courses/**").hasRole("USER")
                                .requestMatchers("/home-page").hasRole("ADMIN")
                                .requestMatchers("/logout").hasRole("ADMIN")
                                .requestMatchers("/courses/**").hasRole("ADMIN")
                                .requestMatchers("/student-info").hasRole("USER")
                                .requestMatchers("/upload-image").hasRole("USER")
                                .requestMatchers("/register-courses").hasRole("USER")
                                .requestMatchers("/error-page").hasRole("USER")
                                .requestMatchers("/studentHome").hasRole("USER")
                                .requestMatchers("/showFormForAdd").hasRole("ADMIN")
                                .requestMatchers("/showFormForAddCourse").hasRole("ADMIN")
                                .requestMatchers("/saveStudent").hasRole("ADMIN")
                                .requestMatchers("/saveCourse").hasRole("ADMIN")
                                .requestMatchers("/update-student").hasRole("ADMIN")
                                .requestMatchers("/update-course/").hasRole("ADMIN")
                                .requestMatchers("/login-form").permitAll()
                                .requestMatchers("/js/**", "/css/**", "/img/**").permitAll()
                )
                // use form based authentication instead of HTTP basic authentication
                .formLogin(formLogin -> formLogin
                        .loginPage("/login-form")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                );

        // use HTTP basic authentication
//        http.httpBasic(Customizer.withDefaults());
        http.httpBasic(httpBasic -> httpBasic.disable());

        // disable Cross Site Request Forgery (CSRF)
        // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(AccountRepository accountRepository) {
        return username -> {
            Account account = accountRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(account);
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            authentication.getAuthorities().forEach(grantedAuthority -> {
                if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                    try {
                        response.sendRedirect("/home-page");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    try {
                        response.sendRedirect("/studentHome");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        };
    }
}
