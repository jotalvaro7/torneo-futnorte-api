package com.futnorte.torneo.domain.exceptions;

public class EntityNotFoundException extends DomainException {
    
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s con ID %d no encontrado", entityName, id));
    }
    
    public EntityNotFoundException(String entityName, String identifier) {
        super(String.format("%s con identificador '%s' no encontrado", entityName, identifier));
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}