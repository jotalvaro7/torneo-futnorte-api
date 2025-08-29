package com.futnorte.torneo.domain.entities;

import java.util.Objects;

public class Jugador {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private Long equipoId;
    private int numeroGoles;
    
    public Jugador() {
        this.numeroGoles = 0;
    }
    
    public Jugador(String nombre, String apellido, String identificacion, Long equipoId) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.equipoId = equipoId;
    }
    
    public void anotarGol() {
        this.numeroGoles++;
    }
    
    public void anotarGoles(int cantidad) {
        if (cantidad >= 0) {
            this.numeroGoles += cantidad;
        }
    }

    public void validarJugador() {
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio");
        }
        if (this.apellido == null || this.apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del jugador es obligatorio");
        }
        if (this.identificacion == null || this.identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación del jugador es obligatoria");
        }
        if (this.equipoId == null) {
            throw new IllegalArgumentException("El jugador debe estar asociado a un equipo");
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
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }
    
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public Long getEquipoId() {
        return equipoId;
    }
    
    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }
    
    public int getNumeroGoles() {
        return numeroGoles;
    }
    
    public void setNumeroGoles(int numeroGoles) {
        this.numeroGoles = numeroGoles;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", equipoId=" + equipoId +
                ", numeroGoles=" + numeroGoles +
                '}';
    }
}