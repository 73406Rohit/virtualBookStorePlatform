package com.VirtualBookStore.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // First restrict admin specific endpoints
                        .requestMatchers("/admin/**", "/book/addbook",
                                "/book/updateBook/**", "/book/deleteBook/**",
                                "/admin/updated/role/**","/api/orders/history/**").hasRole("ADMIN")
                        //  public endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",

                                "/books/**", "/book/getAllBooks",
                                "/book/searchbooks/**",
                                "/book/findBookById/**",
                                "/cart/**",
                                "/api/auth/**",
                                "/api/books/**", "/api/cart/user/**","/api/cart/remove/**","/api/cart/add",
                                "/api/orders/**","/api/orders/user/**",
                                "/api/reviews/**","/api/payments/**"
                                ,
                                // Payment REST API and Razorpay pages
                                "/api/payments/**",
                                "/payment",             //  Razorpay payment page (Thymeleaf)
                                "/orderSuccess",        // Payment success page
                                "/paymentError",        // Error page
                                "/css/**", "/js/**", "/images/**"

                        ).permitAll()
                        .requestMatchers("/auth/**", "/user/**").permitAll()
                        .anyRequest().authenticated()

)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}