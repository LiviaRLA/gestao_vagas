package br.com.livia.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;

import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class DeleteCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> execute(UUID candidateId, String password){

        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(password, candidate.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        var username = candidate.getUsername();
        
        candidateRepository.delete(candidate);

        return ResponseEntity.ok("Candidate [" + username + "] - Deleted");

    } 
}
