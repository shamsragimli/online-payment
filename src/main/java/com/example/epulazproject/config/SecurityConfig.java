package com.example.epulazproject.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(permitAllUrls).permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.PATCH, "user/password/{id}").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/transaction/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "user/card/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/user/balance/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/user/favoritePayment/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/support/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/support/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/support/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/support").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/support").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/support/filtered").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/support/contactInfo").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/payment/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/payment/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/payment/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/payment").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/payment").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/payment/field").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/payment/favoritePayment").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/payment/favoritePayment/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/payment/favoritePayments").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/payment/favoritePayment/{id}").hasAnyAuthority( "ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/payment/fields/{paymentId}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/card/{id}").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/card/{id}").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/card/{id}").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/card").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/card").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/balance/transferToCard").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/balance/transferFromCard").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/balance/fromCashbackToBalance").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/balance").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/balance/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/transaction/withCard").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/transaction/withBalance").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/transaction/favPaymentWithCard").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/transaction/favPaymentWithBalance").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/transaction/cardToCard").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/transaction").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/transaction/{id}").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/transaction/filteredByType").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/autoTransaction").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/autoTransaction").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/autoTransaction/getFiltered").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")



//                                .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
        return http.build();

    }

    static String[] permitAllUrls = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/auth/**"
    };



//    static String[] adminUrls = {
//
//
//    };
//
//    static String[] userUrls = {
//
//
//    };
//
//    static String[] anyAuthUrls = {
//    };
//
//     .requestMatchers(adminUrls).hasAnyAuthority("ROLE_ADMIN")
//                                .requestMatchers(userUrls).hasAnyAuthority("ROLE_USER")
//                                .requestMatchers(adminUrls).hasAnyAuthority("ROLE_ADMIN")
//                                    .requestMatchers(anyAuthUrls).authenticated()


}
