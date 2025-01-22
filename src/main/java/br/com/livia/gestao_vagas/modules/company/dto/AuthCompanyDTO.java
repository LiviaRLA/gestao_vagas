package br.com.livia.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // Cria os getters, setters, equals, hashcode e toString
@AllArgsConstructor // Cria um construtor com todos os atributos da classe
public class AuthCompanyDTO {
    
    private String username;
    private String password;
    
}
