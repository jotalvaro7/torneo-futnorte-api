package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goles_jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GolesJugadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enfrentamiento_id", nullable = false)
    private Long enfrentamientoId;

    @Column(name = "jugador_id", nullable = false)
    private Long jugadorId;

    @Column(name = "cantidad_goles", nullable = false)
    private Integer cantidadGoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enfrentamiento_id", insertable = false, updatable = false)
    private EnfrentamientoEntity enfrentamiento;

    public GolesJugadorEntity(Long enfrentamientoId, Long jugadorId, Integer cantidadGoles) {
        this.enfrentamientoId = enfrentamientoId;
        this.jugadorId = jugadorId;
        this.cantidadGoles = cantidadGoles;
    }
}