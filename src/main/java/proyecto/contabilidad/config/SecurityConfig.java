package proyecto.contabilidad.config;

import proyecto.contabilidad.service.UsuarioService;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public SecurityConfig(JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil, usuarioService);

http.csrf().disable()
    .authorizeHttpRequests()
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/api/cuentas/**").hasRole("ADMIN")
        .requestMatchers("/api/asientos/**").hasAnyRole("ADMIN","CONTADOR")
        .requestMatchers("/api/balance/**").hasAnyRole("ADMIN","CONTADOR")
        .anyRequest().authenticated()
    .and()
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
