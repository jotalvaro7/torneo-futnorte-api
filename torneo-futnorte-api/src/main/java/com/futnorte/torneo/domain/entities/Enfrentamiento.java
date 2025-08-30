package com.futnorte.torneo.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enfrentamiento {
    
    private Long id;
    private Long torneoId;
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private LocalDateTime fechaHora;
    private String cancha;
    private EstadoEnfrentamiento estado;
    
    private Integer golesLocal;
    private Integer golesVisitante;
    
    public Enfrentamiento() {
        this.estado = EstadoEnfrentamiento.PROGRAMADO;
    }
    
    public Enfrentamiento(Long torneoId, Long equipoLocalId, Long equipoVisitanteId, 
                         LocalDateTime fechaHora, String cancha) {
        this();
        this.torneoId = torneoId;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.fechaHora = fechaHora;
        this.cancha = cancha;
    }
    
    public void registrarResultado(int golesLocal, int golesVisitante, 
                                  Equipo equipoLocal, Equipo equipoVisitante) {
        if (this.estado != EstadoEnfrentamiento.PROGRAMADO) {
            throw new IllegalStateException("Solo se puede registrar resultado en enfrentamientos programados");
        }
        
        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos");
        }
        
        if (!equipoLocal.getId().equals(this.equipoLocalId) || 
            !equipoVisitante.getId().equals(this.equipoVisitanteId)) {
            throw new IllegalArgumentException("Los equipos no coinciden con el enfrentamiento");
        }
        
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.estado = EstadoEnfrentamiento.FINALIZADO;
        
        if (golesLocal > golesVisitante) {
            equipoLocal.registrarVictoria(golesLocal, golesVisitante);
            equipoVisitante.registrarDerrota(golesVisitante, golesLocal);
        } else if (golesLocal < golesVisitante) {
            equipoLocal.registrarDerrota(golesLocal, golesVisitante);
            equipoVisitante.registrarVictoria(golesVisitante, golesLocal);
        } else {
            equipoLocal.registrarEmpate(golesLocal, golesVisitante);
            equipoVisitante.registrarEmpate(golesVisitante, golesLocal);
        }
    }
    
    public void actualizarDetalles(LocalDateTime fechaHora, String cancha) {
        if (this.estado == EstadoEnfrentamiento.FINALIZADO) {
            throw new IllegalStateException("No se puede modificar un enfrentamiento finalizado");
        }
        this.fechaHora = fechaHora;
        this.cancha = cancha;
    }
    
    public void cancelar() {
        if (this.estado == EstadoEnfrentamiento.FINALIZADO) {
            throw new IllegalStateException("No se puede cancelar un enfrentamiento finalizado");
        }
        this.estado = EstadoEnfrentamiento.CANCELADO;
    }
    
    public void validarEnfrentamiento() {
        if (this.torneoId == null) {
            throw new IllegalArgumentException("El enfrentamiento debe estar asociado a un torneo");
        }
        if (this.equipoLocalId == null || this.equipoVisitanteId == null) {
            throw new IllegalArgumentException("Debe especificar ambos equipos");
        }
        if (Objects.equals(this.equipoLocalId, this.equipoVisitanteId)) {
            throw new IllegalArgumentException("Un equipo no puede enfrentarse a sí mismo");
        }
        if (this.fechaHora == null) {
            throw new IllegalArgumentException("Debe especificar fecha y hora del enfrentamiento");
        }
        if (this.cancha == null || this.cancha.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar la cancha");
        }
    }
    
    public boolean estaFinalizado() {
        return this.estado == EstadoEnfrentamiento.FINALIZADO;
    }
    
    public boolean estaProgramado() {
        return this.estado == EstadoEnfrentamiento.PROGRAMADO;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTorneoId() {
        return torneoId;
    }
    
    public void setTorneoId(Long torneoId) {
        this.torneoId = torneoId;
    }
    
    public Long getEquipoLocalId() {
        return equipoLocalId;
    }
    
    public void setEquipoLocalId(Long equipoLocalId) {
        this.equipoLocalId = equipoLocalId;
    }
    
    public Long getEquipoVisitanteId() {
        return equipoVisitanteId;
    }
    
    public void setEquipoVisitanteId(Long equipoVisitanteId) {
        this.equipoVisitanteId = equipoVisitanteId;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getCancha() {
        return cancha;
    }
    
    public void setCancha(String cancha) {
        this.cancha = cancha;
    }
    
    public EstadoEnfrentamiento getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoEnfrentamiento estado) {
        this.estado = estado;
    }
    
    public Integer getGolesLocal() {
        return golesLocal;
    }
    
    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }
    
    public Integer getGolesVisitante() {
        return golesVisitante;
    }
    
    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enfrentamiento that = (Enfrentamiento) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Enfrentamiento{" +
                "id=" + id +
                ", torneoId=" + torneoId +
                ", equipoLocalId=" + equipoLocalId +
                ", equipoVisitanteId=" + equipoVisitanteId +
                ", fechaHora=" + fechaHora +
                ", cancha='" + cancha + '\'' +
                ", estado=" + estado +
                ", golesLocal=" + golesLocal +
                ", golesVisitante=" + golesVisitante +
                '}';
    }
}