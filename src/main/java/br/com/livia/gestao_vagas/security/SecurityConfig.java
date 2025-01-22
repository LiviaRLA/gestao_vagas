package br.com.livia.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration // Indica que é uma classe de configuração
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityCompanyFilter securityCompanyFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    private static final String[] PERMIT_LIST = {
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "swagger-resources/**",
        "/actuator/**"
    };

    @Bean // Indica que o método é um Bean gerenciado pelo Spring
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()) // Desabilita o CSRF
            .authorizeHttpRequests(auth -> {
                // Permite todas as requisições para os endpoints de autenticação
                auth.requestMatchers("/candidate/").permitAll()
                    .requestMatchers("/company/").permitAll()
                    .requestMatchers("/company/auth").permitAll()
                    .requestMatchers("/candidate/auth").permitAll()
                    .requestMatchers(PERMIT_LIST).permitAll();
                
                // Exige autenticação para qualquer outra requisição
                auth.anyRequest().authenticated(); 
            })
            
            // Adiciona o filtro de segurança antes do BasicAuthenticationFilter
            .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class) 
            .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna um PasswordEncoder que usa o BCrypt
    }
    
}
