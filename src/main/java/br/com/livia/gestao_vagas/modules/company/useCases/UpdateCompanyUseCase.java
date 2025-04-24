package br.com.livia.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.UserNotFoundException;
import br.com.livia.gestao_vagas.modules.company.dto.UpdateCompanyDTO;
import br.com.livia.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.livia.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class UpdateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(UUID companyId, UpdateCompanyDTO companyDTO) {

        CompanyEntity company = this.companyRepository
            .findById(companyId)
            .orElseThrow(UserNotFoundException::new);
        


        CompanyEntity companySaved = this.companyRepository.save(company);

        return companySaved;
    }
}
