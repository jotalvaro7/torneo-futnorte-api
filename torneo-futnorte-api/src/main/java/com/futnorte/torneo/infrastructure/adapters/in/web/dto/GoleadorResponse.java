package com.futnorte.torneo.infrastructure.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoleadorResponse {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String nacionalidad;
    private int numeroGoles;
    private String nombreEquipo;
}