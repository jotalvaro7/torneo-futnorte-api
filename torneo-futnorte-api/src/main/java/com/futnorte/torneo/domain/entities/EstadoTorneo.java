package com.futnorte.torneo.domain.entities;

public enum EstadoTorneo {
    CREADO("Creado"),
    EN_CURSO("En Curso"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");
    
    private final String descripcion;
    
    EstadoTorneo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}