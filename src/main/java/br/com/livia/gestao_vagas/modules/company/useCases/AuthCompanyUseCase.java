package br.com.livia.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.livia.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.livia.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.livia.gestao_vagas.modules.company.repositories.CompanyRepository;


@Service // Indicates that the class is a service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}") // Injects the value of the property security.token.secret
    private String secretKey;

    @Autowired 
    private CompanyRepository companyRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        // Find company by username
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            }
        );

        // Verify password
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Create token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas") // who issues the token
            .withExpiresAt(expiresIn) // when the token expires (2 hours)
            .withSubject(company.getId().toString()) // who the token is for
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm); // sign the token with the secret key

            var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;
        
    }

    
    
}
