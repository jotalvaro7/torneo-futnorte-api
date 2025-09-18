package com.futnorte.torneo.domain.entities;

import java.util.Objects;

public class GolesJugador {

    private Long id;
    private Long enfrentamientoId;
    private Long jugadorId;
    private Integer cantidadGoles;

    public GolesJugador() {
    }

    public GolesJugador(Long enfrentamientoId, Long jugadorId, Integer cantidadGoles) {
        this.enfrentamientoId = enfrentamientoId;
        this.jugadorId = jugadorId;
        this.cantidadGoles = cantidadGoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnfrentamientoId() {
        return enfrentamientoId;
    }

    public void setEnfrentamientoId(Long enfrentamientoId) {
        this.enfrentamientoId = enfrentamientoId;
    }

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }

    public Integer getCantidadGoles() {
        return cantidadGoles;
    }

    public void setCantidadGoles(Integer cantidadGoles) {
        this.cantidadGoles = cantidadGoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GolesJugador that = (GolesJugador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GolesJugador{" +
                "id=" + id +
                ", enfrentamientoId=" + enfrentamientoId +
                ", jugadorId=" + jugadorId +
                ", cantidadGoles=" + cantidadGoles +
                '}';
    }
}