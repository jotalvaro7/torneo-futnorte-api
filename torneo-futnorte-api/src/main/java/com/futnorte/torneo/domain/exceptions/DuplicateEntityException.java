package com.futnorte.torneo.domain.exceptions;

public class DuplicateEntityException extends DomainException {
    
    public DuplicateEntityException(String entityName, String identifier) {
        super(String.format("Ya existe un %s con el identificador '%s'", entityName, identifier));
    }
    
    public DuplicateEntityException(String message) {
        super(message);
    }
}