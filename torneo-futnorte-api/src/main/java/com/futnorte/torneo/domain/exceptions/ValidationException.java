package com.futnorte.torneo.domain.exceptions;

public class ValidationException extends DomainException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String field, String reason) {
        super(String.format("Error de validación en campo '%s': %s", field, reason));
    }
}