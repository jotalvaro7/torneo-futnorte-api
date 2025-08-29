-- Datos de prueba para la aplicación de torneos de fútbol

INSERT INTO torneos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('Liga Premier 2024', 'Torneo de fútbol profesional temporada 2024', '2024-03-01T10:00:00', '2024-11-30T22:00:00', 'EN_CURSO'),
('Copa América Junior', 'Torneo de fútbol juvenil', '2024-06-01T09:00:00', '2024-08-31T20:00:00', 'EN_CURSO'),
('Torneo Regional Norte', 'Competencia regional de equipos del norte', '2024-02-15T08:00:00', '2024-05-15T19:00:00', 'EN_CURSO');

INSERT INTO equipos (nombre, entrenador, torneo_id, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra) VALUES
('Real Madrid FC', 'Carlos Ancelotti', 1, 45, 15, 14, 3, 1, 42, 8),
('FC Barcelona', 'Xavi Hernández', 1, 38, 15, 12, 2, 4, 35, 15),
('Atlético Madrid', 'Diego Simeone', 1, 32, 15, 9, 5, 4, 28, 18),
('Valencia CF', 'Rubén Baraja', 1, 25, 15, 7, 4, 7, 22, 25),
('Sevilla FC', 'José Luis Mendilibar', 1, 20, 15, 5, 5, 8, 18, 28);

INSERT INTO equipos (nombre, entrenador, torneo_id, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra) VALUES
('Águilas Juvenil', 'Miguel Torres', 2, 0, 0, 0, 0, 0, 0, 0),
('Leones Sub-20', 'Ana García', 2, 0, 0, 0, 0, 0, 0, 0),
('Tigres Junior', 'Luis Rodríguez', 2, 0, 0, 0, 0, 0, 0, 0),
('Pumas Cadete', 'María López', 2, 0, 0, 0, 0, 0, 0, 0);

INSERT INTO equipos (nombre, entrenador, torneo_id, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra) VALUES
('Norte United', 'Roberto Silva', 3, 30, 12, 10, 0, 2, 28, 8),
('Montaña FC', 'Patricia Díaz', 3, 25, 12, 8, 1, 3, 22, 12),
('Cordillera CF', 'Andrés Morales', 3, 18, 12, 5, 3, 4, 15, 18),
('Nevados FC', 'Carmen Ruiz', 3, 10, 12, 3, 1, 8, 10, 22);