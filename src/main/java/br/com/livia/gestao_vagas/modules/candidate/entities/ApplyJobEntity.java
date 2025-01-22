package br.com.livia.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.livia.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "apply_jobs")
public class ApplyJobEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne //Um candidato pode aplicar para muitas vagas
    @JoinColumn(name = "candidateId", insertable=false, updatable=false )
    private CandidateEntity candidateEntity;

    @ManyToOne
    @JoinColumn(name = "jobId", insertable=false, updatable=false )
    private JobEntity jobEntity;

    @Column(name = "candidateId" )
    private UUID candidateId;

    @Column(name = "jobId" )
    private UUID jobId;



    @CreationTimestamp
    private LocalDateTime createdAt;

}
