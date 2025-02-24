package br.com.livia.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.JobNotFoundException;
import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;
import br.com.livia.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.livia.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.livia.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.livia.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;



    public ApplyJobEntity execute(UUID idCandidate, UUID idJob){

        // Validar se o candidato existe
        this.candidateRepository.findById(idCandidate)
            .orElseThrow(UserNotFoundException::new);

        // Validar se a vaga existe
        this.jobRepository.findById(idJob)
            .orElseThrow(JobNotFoundException::new);

        // Inscrição do candidato na vaga
        var applyJob = ApplyJobEntity.builder()
            .candidateId(idCandidate)
            .jobId(idJob)
            .build();

        applyJob = applyJobRepository.save(applyJob);
        return applyJob; 
    }
    
}
