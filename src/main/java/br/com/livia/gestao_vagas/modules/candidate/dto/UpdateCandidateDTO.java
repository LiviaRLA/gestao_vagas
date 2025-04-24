package br.com.livia.gestao_vagas.modules.candidate.dto;

import lombok.Data;

@Data
public class UpdateCandidateDTO {

    private String username;
    private String email;
    private String description;
    private String curriculum;
    
}
