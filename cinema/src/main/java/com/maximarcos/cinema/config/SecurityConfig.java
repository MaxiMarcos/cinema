package com.maximarcos.cinema.config;

import com.maximarcos.cinema.jwt.JwtRequestFilter;
import com.maximarcos.cinema.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso público a GET de películas y horarios
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/movie/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/schedule/**").permitAll()
                    .requestMatchers(
                            "/img/**",
                            "/static/**",
                            "/uploads/**"
                    ).permitAll()
                // Requerir rol ADMIN para POST, PUT, DELETE de películas y horarios
                //.requestMatchers("/movie/**").hasRole("ADMIN")
                //.requestMatchers("/schedule/**").hasRole("ADMIN")
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Usar sesiones sin estado para JWT
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Añadir filtro JWT
        return http.build();
    }

    // Este PasswordEncoder no es estrictamente necesario aquí ya que no estamos autenticando,
    // pero Spring Security lo requiere para la configuración.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Este AuthenticationManager tampoco es estrictamente necesario para la validación de JWT,
    // pero puede ser útil si en el futuro se necesita autenticación interna.
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }
}
