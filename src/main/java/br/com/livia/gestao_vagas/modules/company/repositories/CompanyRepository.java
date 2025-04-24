package br.com.livia.gestao_vagas.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.livia.gestao_vagas.modules.company.entities.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {

    Optional<CompanyEntity> findByEmailOrUsername(String email, String username);
    Optional<CompanyEntity> findByUsername(String username);
    Optional<CompanyEntity> findById(UUID id);

}
