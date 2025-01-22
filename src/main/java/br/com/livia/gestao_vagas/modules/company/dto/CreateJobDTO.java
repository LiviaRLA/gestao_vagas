package br.com.livia.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga para Dev", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Home office, gym pass...")
    private String benefits;

    @Schema(example = "Sênior", requiredMode = RequiredMode.REQUIRED)
    private String level;
    
}
