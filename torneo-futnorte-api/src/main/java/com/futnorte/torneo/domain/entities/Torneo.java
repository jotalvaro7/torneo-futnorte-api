package com.futnorte.torneo.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Torneo {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoTorneo estado;
    
    public Torneo() {
        this.estado = EstadoTorneo.CREADO;
    }
    
    public Torneo(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    public void iniciarTorneo(int cantidadEquipos) {
        if (this.estado == EstadoTorneo.FINALIZADO) {
            throw new IllegalStateException("El torneo solo puede iniciarse si está en estado CREADO O CANCELADO");
        }
        if (cantidadEquipos < 2) {
            throw new IllegalStateException("El torneo debe tener al menos 2 equipos para iniciarse");
        }
        this.estado = EstadoTorneo.EN_CURSO;
    }
    
    public void finalizarTorneo() {
        if (this.estado != EstadoTorneo.EN_CURSO) {
            throw new IllegalStateException("El torneo solo puede finalizarse si está EN_CURSO");
        }
        this.estado = EstadoTorneo.FINALIZADO;
    }
    
    public void cancelarTorneo() {
        if (this.estado == EstadoTorneo.FINALIZADO) {
            throw new IllegalStateException("No se puede cancelar un torneo que ya está finalizado");
        }
        this.estado = EstadoTorneo.CANCELADO;
    }
    
    public boolean puedeSerEliminado() {
        return this.estado == EstadoTorneo.CANCELADO || this.estado == EstadoTorneo.FINALIZADO;
    }
    
    public void validarEliminacion() {
        if (!puedeSerEliminado()) {
            throw new IllegalStateException("Solo se pueden eliminar torneos en estado CANCELADO o FINALIZADO");
        }
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public EstadoTorneo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoTorneo estado) {
        this.estado = estado;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Torneo torneo = (Torneo) o;
        return Objects.equals(id, torneo.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Torneo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado=" + estado +
                '}';
    }
}