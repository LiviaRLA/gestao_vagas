package br.com.livia.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;
import br.com.livia.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    
    public ProfileCandidateResponseDTO execute(UUID candidateId){

        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(UserNotFoundException::new);

        var candidateDTO = ProfileCandidateResponseDTO.builder()
            .description(candidate.getDescription())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .name(candidate.getName())
            .build();

            return candidateDTO;
    }
}
