package com.futnorte.torneo.domain.entities;

import java.util.Objects;

public class Equipo {

    private Long id;
    private String nombre;
    private String entrenador;
    private Long torneoId;

    private int puntos;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesAFavor;
    private int golesEnContra;

    public Equipo() {
        this.puntos = 0;
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.partidosPerdidos = 0;
        this.golesAFavor = 0;
        this.golesEnContra = 0;
    }

    public Equipo(String nombre, String entrenador, Long torneoId) {
        this();
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.torneoId = torneoId;
    }


    public void registrarVictoria(int golesAnotados, int golesRecibidos) {
        this.partidosJugados++;
        this.partidosGanados++;
        this.puntos += 3;
        this.golesAFavor += golesAnotados;
        this.golesEnContra += golesRecibidos;
    }

    public void registrarEmpate(int golesAnotados, int golesRecibidos) {
        this.partidosJugados++;
        this.partidosEmpatados++;
        this.puntos += 1;
        this.golesAFavor += golesAnotados;
        this.golesEnContra += golesRecibidos;
    }

    public void registrarDerrota(int golesAnotados, int golesRecibidos) {
        this.partidosJugados++;
        this.partidosPerdidos++;
        this.golesAFavor += golesAnotados;
        this.golesEnContra += golesRecibidos;
    }

    public int getDiferenciaGoles() {
        return this.golesAFavor - this.golesEnContra;
    }

    public void validarEquipo() {
        if (this.nombre == null || this.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo es obligatorio");
        }
        if (this.entrenador == null || this.entrenador.trim().isEmpty()) {
            throw new IllegalArgumentException("El entrenador del equipo es obligatorio");
        }
        if (this.torneoId == null) {
            throw new IllegalArgumentException("El equipo debe estar asociado a un torneo");
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

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public Long getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public void setGolesAFavor(int golesAFavor) {
        this.golesAFavor = golesAFavor;
    }

    public int getGolesEnContra() {
        return golesEnContra;
    }

    public void setGolesEnContra(int golesEnContra) {
        this.golesEnContra = golesEnContra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(id, equipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", entrenador='" + entrenador + '\'' +
                ", torneoId=" + torneoId +
                ", puntos=" + puntos +
                ", partidosJugados=" + partidosJugados +
                ", partidosGanados=" + partidosGanados +
                ", partidosEmpatados=" + partidosEmpatados +
                ", partidosPerdidos=" + partidosPerdidos +
                ", golesAFavor=" + golesAFavor +
                ", golesEnContra=" + golesEnContra +
                ", diferenciagoles=" + getDiferenciaGoles() +
                '}';
    }
}