package br.com.livia.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.livia.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.livia.gestao_vagas.modules.company.entities.JobEntity;
import br.com.livia.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.livia.gestao_vagas.modules.company.useCases.ListJobsByCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @Autowired
    private ListJobsByCompanyUseCase listJobsByCompanyUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(
        name = "Vagas",
        description = "Informações das vagas"
    )
    @Operation(
        summary = "Cadastro de vaga",
        description = "Essa função é responsável por cadastrar as vagas dentro da empresa"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = JobEntity.class))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {

        var companyId = request.getAttribute("companyId");

        try {
            JobEntity job = this.createJobUseCase.execute(createJobDTO, UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(job);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(
        name = "Vagas",
        description = "Listagem das vagas"
    )
    @Operation(
        summary = "Cadastro de vaga",
        description = "Essa função é responsável por listar as vagas dentro da empresa"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = JobEntity.class))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> listByCompany(HttpServletRequest request) {

        var companyId = request.getAttribute("companyId");

        try {
            var result = this.listJobsByCompanyUseCase.execute(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    
}
