package br.com.livia.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.EmailOrUsernameAlreadyInUseException;
import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;
import br.com.livia.gestao_vagas.modules.candidate.dto.UpdateCandidateDTO;
import br.com.livia.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class UpdateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(UUID candidateId, UpdateCandidateDTO candidateDTO){

        CandidateEntity candidate = this.candidateRepository
            .findById(candidateId)
            .orElseThrow(UserNotFoundException::new);

        this.candidateRepository
            .findByEmailOrUsername(candidateDTO.getEmail(), candidateDTO.getUsername())
            .ifPresent(existingCandidate -> {
                throw new EmailOrUsernameAlreadyInUseException();
            });

        if (candidateDTO!=null) {

            if (candidateDTO.getUsername()!=null) {
                 candidate.setUsername(candidateDTO.getUsername());
            }
            if (candidateDTO.getEmail()!=null) {
                candidate.setEmail(candidateDTO.getEmail());
            }
            candidate.setDescription(candidateDTO.getDescription());
            candidate.setCurriculum(candidateDTO.getCurriculum());
        }


        CandidateEntity candidateUpdated = this.candidateRepository.save(candidate);
        
        return candidateUpdated;
    }
}
