package br.com.livia.gestao_vagas.modules.candidate.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCandidateResponseDTO {
    
    private String accessToken;
    private Long expiresIn;
    private List<String> roles;
    
}
