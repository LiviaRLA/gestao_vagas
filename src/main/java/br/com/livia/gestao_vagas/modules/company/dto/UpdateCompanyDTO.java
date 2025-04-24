package br.com.livia.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class UpdateCompanyDTO {

    @Schema(example = "Vaga para Dev", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Home office, gym pass...")
    private String benefits;
    
}
