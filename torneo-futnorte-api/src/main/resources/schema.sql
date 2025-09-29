-- Crear tabla para goles de jugadores
-- Esta tabla debe existir antes de ejecutar data.sql
CREATE TABLE IF NOT EXISTS goles_jugador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enfrentamiento_id BIGINT NOT NULL,
    jugador_id BIGINT NOT NULL,
    cantidad_goles INTEGER NOT NULL CHECK (cantidad_goles > 0),
    FOREIGN KEY (enfrentamiento_id) REFERENCES enfrentamientos(id) ON DELETE CASCADE
);

-- Crear índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_goles_jugador_enfrentamiento_id ON goles_jugador(enfrentamiento_id);
CREATE INDEX IF NOT EXISTS idx_goles_jugador_jugador_id ON goles_jugador(jugador_id);