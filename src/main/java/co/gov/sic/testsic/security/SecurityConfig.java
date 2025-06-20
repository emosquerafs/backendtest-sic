package co.gov.sic.testsic.security;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final Logger logger = LogManager.getLogger(SecurityConfig.class);


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security for other endpoints with context-path: {}", contextPath);


        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> {
                    // Rutas públicas para Swagger/OpenAPI y endpoints GET de Banner
                    authorizeRequests
                            .requestMatchers(
                                    contextPath + "/**",
                                    contextPath + "/swagger-ui.html",
                                    contextPath + "/swagger-ui/**",
                                    contextPath + "/v3/api/**",
                                    contextPath + "/v3/api-docs/**",
                                    contextPath + "/v3/api-docs",
                                    contextPath + "/v3/api/swagger-config",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/v3/api/**",
                                    "/v3/api-docs/**",
                                    "/v3/api-docs",
                                    "/v3/api/swagger-config",
                                    "/webjars/**"
                            ).permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))
                );

        return http.build();
    }
}


