package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String entrenador;
    
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer puntos = 0;
    
    @Column(name = "partidos_jugados", nullable = false, columnDefinition = "integer default 0")
    private Integer partidosJugados = 0;
    
    @Column(name = "partidos_ganados", nullable = false, columnDefinition = "integer default 0")
    private Integer partidosGanados = 0;
    
    @Column(name = "partidos_empatados", nullable = false, columnDefinition = "integer default 0")
    private Integer partidosEmpatados = 0;
    
    @Column(name = "partidos_perdidos", nullable = false, columnDefinition = "integer default 0")
    private Integer partidosPerdidos = 0;
    
    @Column(name = "goles_a_favor", nullable = false, columnDefinition = "integer default 0")
    private Integer golesAFavor = 0;
    
    @Column(name = "goles_en_contra", nullable = false, columnDefinition = "integer default 0")
    private Integer golesEnContra = 0;
}