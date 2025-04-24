package br.com.livia.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DeleteCandidateResponse {

    private UUID id;
    private String username;
    
}
