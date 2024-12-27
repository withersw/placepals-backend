package com.wadewithers.placepals.configuration;


import com.wadewithers.placepals.models.User;
import com.wadewithers.placepals.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.wadewithers.placepals.constants.Constants.X_REQUESTED_WITH;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;


@Configuration
public class WebSecurityConfiguration {

//    private final UserService userService;
//    //private final AuthEntryPoint authEntryPoint;
//
//    public WebSecurityConfiguration(UserService userService, AuthEntryPoint authEntryPoint) {
//        this.userService = userService;
//        this.authEntryPoint = authEntryPoint;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing purposes
                //.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom configuration
                .cors(Customizer.withDefaults()) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/profiles").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/profiles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/profiles/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/profiles/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/profiles/me").permitAll()
                                .requestMatchers(HttpMethod.GET, "/profiles/me").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/profiles/me").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/profiles/photo").permitAll()
                                .requestMatchers(HttpMethod.POST, "/profiles/photo").permitAll()
                                .requestMatchers(HttpMethod.GET, "/profiles/photo").permitAll()
                                .requestMatchers(HttpMethod.GET, "/profiles/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/profiles/**").permitAll()


//                        .requestMatchers(HttpMethod.GET, "/auth").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .anyRequest().authenticated()
                );
//                .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint));

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // No encryption for now
//    }

    @Bean
    public CorsFilter corsFilter() {
        var urlBasedCorsConfigSrc = new UrlBasedCorsConfigurationSource();
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfig.setAllowedHeaders(List.of(ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION, X_REQUESTED_WITH, ACCESS_CONTROL_REQUEST_METHOD, ACCESS_CONTROL_REQUEST_HEADERS, ACCESS_CONTROL_ALLOW_CREDENTIALS));
        corsConfig.setExposedHeaders(List.of(ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION, X_REQUESTED_WITH, ACCESS_CONTROL_REQUEST_METHOD, ACCESS_CONTROL_REQUEST_HEADERS, ACCESS_CONTROL_ALLOW_CREDENTIALS));
        corsConfig.setAllowedMethods(List.of(GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name(), OPTIONS.name()));
        urlBasedCorsConfigSrc.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(urlBasedCorsConfigSrc);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

}
