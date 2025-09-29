package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import com.futnorte.torneo.domain.entities.EstadoEnfrentamiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "enfrentamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnfrentamientoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;
    
    @Column(name = "equipo_local_id", nullable = false)
    private Long equipoLocalId;
    
    @Column(name = "equipo_visitante_id", nullable = false)
    private Long equipoVisitanteId;
    
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(nullable = false)
    private String cancha;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnfrentamiento estado = EstadoEnfrentamiento.PROGRAMADO;
    
    @Column(name = "goles_local")
    private Integer golesLocal;
    
    @Column(name = "goles_visitante")
    private Integer golesVisitante;

    @OneToMany(mappedBy = "enfrentamientoId", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GolesJugadorEntity> golesJugadores;
}