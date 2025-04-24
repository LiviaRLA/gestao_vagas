package br.com.livia.gestao_vagas.exceptions;

public class EmailOrUsernameAlreadyInUseException  extends RuntimeException {

    public EmailOrUsernameAlreadyInUseException() {
        super("Email/username already in use");
    }
    
}
