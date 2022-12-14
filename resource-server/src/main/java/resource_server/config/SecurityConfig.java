package resource_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/pets").hasAuthority("ROLE_pet-store.user")
                        .antMatchers(HttpMethod.POST, "/pets").hasAuthority("ROLE_pet-store.admin")
                        .anyRequest().authenticated())
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());
        return jwtConverter;
    }
}
