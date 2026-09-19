package com.agencia.travelapi.config;

import com.agencia.travelapi.model.Usuario;
import com.agencia.travelapi.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetailsService(UsuarioRepository repo){
        return username -> {
            Usuario u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));
            if(!u.isAtivo()) throw new DisabledException("Usuário desativado");
            return User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().name()).build();
        };
    }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
            .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/destinos","/api/destinos/{id}").permitAll()
                .anyRequest().authenticated())
            .httpBasic(basic->{});
        return http.build();
    }
}
