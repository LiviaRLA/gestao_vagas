package br.com.livia.gestao_vagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean //sobrescreve uma implementação que já existe
    public OpenAPI openAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Gestão de vagas")
                .description("API responsável pela gestão de vagas")
                .version("1")
            )
            //Adiciona camada de segurança onde foi definido o schema "jwt_auth"
            .schemaRequirement("jwt_auth", createSecurityScheme());
            
            /* Adiciona camada de segurança para todas as rotas
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(new Components().addSecuritySchemes("Bearer Authentication", createSecurityScheme()));*/
    }

    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme()
            .name("jwt_auth")
            .scheme("bearer")
            .bearerFormat("JWT")
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER);
    }
    
}
