package br.com.livia.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;
import br.com.livia.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.livia.gestao_vagas.modules.company.entities.JobEntity;
import br.com.livia.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.livia.gestao_vagas.modules.company.repositories.JobRepository;


@Service
public class CreateJobUseCase {


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;
    
    public JobEntity execute(CreateJobDTO createJobDTO, UUID companyId) {

        this.companyRepository.findById(companyId)
            .orElseThrow(UserNotFoundException::new);

        var job = JobEntity.builder()
            .description(createJobDTO.getDescription())
            .benefits(createJobDTO.getBenefits())
            .level(createJobDTO.getLevel())
            .companyId(companyId)
            .build();

        return this.jobRepository.save(job);
    }
        
    
}