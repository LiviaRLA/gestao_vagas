package br.com.livia.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
@Entity(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Schema(example = "Vaga para design")
    private String description;

    @NotBlank(message = "Level is mandatory")
    @Schema(example = "JUNIOR")
    private String level;
    
    @Schema(example = "Gym pass, ...")
    private String benefits;

    @ManyToOne() // many jobs to one company
    @JoinColumn(name = "companyId", insertable = false, updatable = false)
    private CompanyEntity companyEntity;

    @Column(name = "companyId", nullable = false)
    private UUID companyId;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
