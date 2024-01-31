package org.halimatussyadiyah.pmb.configuration;

import lombok.RequiredArgsConstructor;
import org.halimatussyadiyah.pmb.filter.CustomAuthorizationFilter;
import org.halimatussyadiyah.pmb.handler.CustomAccessDeniedHandler;
import org.halimatussyadiyah.pmb.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.halimatussyadiyah.pmb.constant.Constants.PUBLIC_URLS;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final CustomAuthorizationFilter customAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(OPTIONS).permitAll()
                        .requestMatchers(DELETE, "/user/delete/**").hasAuthority("DELETE:USER")
                        .requestMatchers(DELETE, "/biodata/delete/**").hasAuthority("DELETE:BIODATA")
                        .anyRequest().authenticated())
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}



 /* Documentations
    https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.html

    https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html

    https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationProvider.html

    https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html

    https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/AbstractUserDetailsAuthenticationProvider.html
    */


//        Feel free to use the Lambda notation for spring 3.0
//        http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults());
//        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
//        http.authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_URLS).permitAll());
//        http.authorizeHttpRequests(request -> request.requestMatchers(OPTIONS).permitAll());
//        http.authorizeHttpRequests(request -> request.requestMatchers(DELETE, "/user/delete/**").hasAuthority("DELETE:USER"));
//        http.authorizeHttpRequests(request -> request.requestMatchers(DELETE, "/customer/delete/**").hasAuthority("DELETE:CUSTOMER"));
//        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customAuthenticationEntryPoint));
//
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        return http.build();


//        http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults());


//        CONFIG AT SPRING BOOT 2
//        http.csrf().disable().cors().disable();
//        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.authorizeHttpRequests().requestMatchers(PUBLIC_URLS).permitAll();
//        http.authorizeHttpRequests().requestMatchers(DELETE, "/user/delete/**").hasAnyAuthority("DELETE:USER");
//        http.authorizeHttpRequests().requestMatchers(DELETE, "/customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER");
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customAuthenticationEntryPoint);
//        http.authorizeHttpRequests().anyRequest().authenticated();
//        return http.build();
