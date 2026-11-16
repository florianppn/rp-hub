package com.rphub.api.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("nom d'utilisateur ou mot de passe invalide.");
    }
}
