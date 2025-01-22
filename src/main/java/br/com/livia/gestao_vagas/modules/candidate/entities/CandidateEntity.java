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

@Data // Lombok é uma biblioteca que gera automaticamente os métodos getter, setter, equals, hashcode e toString
@Entity(name = "candidate") // A anotação @Entity é usada para marcar a classe como uma entidade JPA
public class CandidateEntity {
    @Id // A anotação @Id é usada para especificar a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.UUID) // A anotação @GeneratedValue é usada para especificar a estratégia de geração de valor da chave primária
    @Schema(hidden = true) // A anotação @Schema com hidden = true é usada para ocultar o campo no Swagger
    private UUID id; // UUID é um tipo de dado que gera um identificador único universal
    //@Column(name = "name")  A anotação @Column é usada para especificar o nome da coluna no banco de dados
    @Schema(example = "Maria Silva")
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S{3,15}$", message = "Username should be valid and not contain spaces") // A anotação @Pattern valida se o valor do campo corresponde ao padrão regex fornecido
    @Schema(example = "mariazinha")
    private String username;

    @Email(message = "Email should be valid")
    @Schema(example = "maria@gmail.com")
    private String email;

    @Length(min = 6, max = 100, message = "Password should be between 6 and 100 characters") // A anotação @Length valida se o tamanho da string está entre os valores especificados
    @Schema(example = "AdMiN@12345", minLength = 6, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;
    
    private String description;
    private String curriculum;

    @CreationTimestamp // A anotação @CreationTimestamp é usada para marcar a data e hora em que a entidade foi criada
    @Schema(hidden = true)
    private LocalDateTime createdAt;
}
