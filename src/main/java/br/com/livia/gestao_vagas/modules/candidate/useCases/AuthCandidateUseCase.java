package br.com.livia.gestao_vagas.modules.candidate.useCases;

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

import br.com.livia.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.livia.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            });

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException("Username/password incorrect");
        }

        var roles =  Arrays.asList("CANDIDATE");

        // Generate token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(1));
        var token = JWT.create()
        
            .withSubject(candidate.getId().toString())
            .withIssuer("javagas")
            .withClaim("roles", roles)
            .withExpiresAt(expiresIn)
            .sign(algorithm);

        // Return token
        var authCandidateResponse = AuthCandidateResponseDTO.builder()
            .accessToken(token)
            .expiresIn(expiresIn.toEpochMilli())
            .roles(roles)
            .build();

        return authCandidateResponse;

    }
}
