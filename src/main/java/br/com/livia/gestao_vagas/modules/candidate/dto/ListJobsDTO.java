package br.com.livia.gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListJobsDTO {

    private String title;
    private String description;
    private String level;
    private String benefits;
    private UUID companyId;
    private LocalDateTime createdAt;
}
