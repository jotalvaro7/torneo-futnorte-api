package com.futnorte.torneo.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false, unique = true)
    private String identificacion;
    
    @Column(nullable = false)
    private String nacionalidad;
    
    @Column(name = "equipo_id", nullable = false)
    private Long equipoId;
    
    @Column(name = "numero_goles", nullable = false, columnDefinition = "integer default 0")
    private Integer numeroGoles = 0;
}