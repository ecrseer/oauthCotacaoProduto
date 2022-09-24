package br.infnet.edu.gabrieljasscloudCotacao.cotacaoClo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableWebSecurity
@EnableAuthorizationServer
@EnableResourceServer
@Configuration
public class SecurityConfiguration {
    
    @Bean
    public
    WebSecurityCustomizer
    webSecurityCustomizer() {
        return (web) -> 
            web
                .ignoring()
                .antMatchers(
                    "/",
                    "/swagger-ui/**",
                    "/swagger-ui.html/**", 
                    "/docs/**",
                    "/usuarios**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // return NoOpPasswordEncoder.getInstance();
    }
}
