package br.com.livia.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(hidden = true)
    private UUID id;

    @Schema(example = "Maria Silva")
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S{3,15}$", message = "Username should be valid and not contain spaces")
    @Schema(example = "maria")
    private String username;

    @Email(message = "Email should be valid")
    @Schema(example = "maria@gmail.com")
    private String email;

    @Length(min = 8, max = 100, message = "Password should be between 8 and 100 characters") 
    @Schema(example = "AdMiN@12345", minLength = 8, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;
    
    private String description;
    private String curriculum;

    @CreationTimestamp
    @Schema(hidden = true)
    private LocalDateTime createdAt;
}
