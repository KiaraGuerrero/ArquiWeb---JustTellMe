package pe.edu.upc.demo3798api.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired private JwtAuthenticationEntryPoint entryPoint;
    @Autowired private UserDetailsService jwtUserDetailsService;
    @Autowired private JwtRequestFilter jwtRequestFilter;
    @Autowired @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    // 1) Password encoder
    @Bean public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // usando BCrypt :contentReference[oaicite:0]{index=0}
    }

    // 2) Exponer AuthenticationManager
    @Bean public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 3) Configurar userDetails + encoder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // 4) Ignorar por completo estos endpoints (no pasan ni por el filtro JWT) :contentReference[oaicite:1]{index=1}
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/api/usuarios/register",
                "/api/usuarios/login",
                "/error"                  // permite página de error sin auth :contentReference[oaicite:2]{index=2}
        );
    }

    // 5) Chain: deshabilitar CSRF, formularios y basic; resto requiere JWT
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1) endpoints públicos de login/registro
                        .requestMatchers("/api/usuarios/register", "/api/usuarios/login").permitAll()

                        // 2) endpoints de Swagger (JSON + UI)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()

                        // 3) el resto requiere JWT válido
                        .anyRequest().authenticated()
                )

                // inyectar el filtro JWT antes de UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                // manejo de errores de autenticación
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))

                // no creamos sesión
                .sessionManagement(Customizer.withDefaults())
        ;

        return http.build();
    }
}
