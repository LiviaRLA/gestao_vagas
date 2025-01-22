package br.com.livia.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.livia.gestao_vagas.exceptions.UserFoundException;
import br.com.livia.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.livia.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service 
public class CreateCompanyUseCase {
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {

        this.companyRepository
            .findByEmailOrUsername(companyEntity.getEmail(), companyEntity.getUsername())
            .ifPresent(company -> {
                throw new UserFoundException();
            });

            var password = passwordEncoder.encode(companyEntity.getPassword()); // Encripta a senha
            companyEntity.setPassword(password); // Atualiza a senha encriptada
        
        return this.companyRepository.save(companyEntity);
    }
}
