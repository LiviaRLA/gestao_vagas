package br.com.livia.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.livia.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.livia.gestao_vagas.modules.candidate.dto.UpdateCandidateDTO;
import br.com.livia.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.livia.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.livia.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.livia.gestao_vagas.modules.candidate.useCases.DeleteCandidateUseCase;
import br.com.livia.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.livia.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.livia.gestao_vagas.modules.candidate.useCases.UpdateCandidateUseCase;
import br.com.livia.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/candidate")
@Tag(
    name = "Candidato",
    description = "Informações do candidato"
)
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private UpdateCandidateUseCase updateCandidateUseCase;

    @Autowired
    private DeleteCandidateUseCase deleteCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(
        summary = "Cadastro de candidato",
        description = "Essa função é responsável por cadastrar novos candidatos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {

        try {
            var result = this.createCandidateUseCase.execute(candidateEntity); 
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }	
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Perfil do candidato",
        description = "Essa função é responsável por buscar as informações do perfil do candidato"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "User Not Found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var candidateId = request.getAttribute("candidateId");
        try{
            var profile = this.profileCandidateUseCase
                .execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }	
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Atualizar informações do candidato",
        description = "Essa função é responsável por atualizar as informações do candidato"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    @PutMapping("/update")
    public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody UpdateCandidateDTO candidateDTO) {

        var candidateId = request.getAttribute("candidateId");

        try {    
            var candidate = this.updateCandidateUseCase
            .execute(UUID.fromString(candidateId.toString()), candidateDTO);

            return ResponseEntity.ok().body(candidate);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Excluir cadastro do candidato",
        description = "Essa função é responsável por excluir o cadastro do candidato"
    )
    @SecurityRequirement(name = "jwt_auth")
    @DeleteMapping("/delete")
    public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody String password) {

        var candidateId = request.getAttribute("candidateId");

        try {    
            var candidate = this.deleteCandidateUseCase
            .execute(UUID.fromString(candidateId.toString()), password);

            return ResponseEntity.ok().body(candidate);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Listagem de vagas disponíveis para o candidato",
        description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = 
            @Schema(implementation = JobEntity.class)))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
        summary = "Inscrição do candidato para uma vaga",
        description = "Essa função é responsável por realizar a inscrição do candidato em uma vaga"
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId){
        
        var candidateId = request.getAttribute("candidateId");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);
            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
