package br.com.livia.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.UserFoundException;
import br.com.livia.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        
        this.candidateRepository
            .findByEmailOrUsername(candidateEntity.getEmail(), candidateEntity.getUsername())
            .ifPresent(candidate -> {
                throw new UserFoundException();
            });

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        CandidateEntity candidate = this.candidateRepository.save(candidateEntity);
        
        return candidate;
    }
}
