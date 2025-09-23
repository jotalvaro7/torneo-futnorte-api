INSERT INTO torneos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('La Liga 2025', 'Torneo de fútbol profesional temporada 2025', '2025-09-13T10:00:00', '2025-12-30T23:59:59', 'EN_CURSO'),
('Copa América Junior', 'Torneo de fútbol juvenil', '2025-09-13T10:00:00', '2025-12-30T23:59:59', 'EN_CURSO');

INSERT INTO equipos (nombre, entrenador, torneo_id, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra) VALUES
('Real Madrid FC', 'Carlos Ancelotti', 1, 0, 0, 0, 0, 0, 0, 0),
('FC Barcelona', 'Xavi Hernández', 1, 0, 0, 0, 0, 0, 0, 0),
('Atlético Madrid', 'Diego Simeone', 1, 0, 0, 0, 0, 0, 0, 0),
('Valencia CF', 'Rubén Baraja', 1, 0, 0, 0, 0, 0, 0, 0);

INSERT INTO equipos (nombre, entrenador, torneo_id, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra) VALUES
('Águilas Juvenil', 'Miguel Torres', 2, 0, 0, 0, 0, 0, 0, 0),
('Leones Sub-20', 'Ana García', 2, 0, 0, 0, 0, 0, 0, 0),
('Tigres Junior', 'Luis Rodríguez', 2, 0, 0, 0, 0, 0, 0, 0),
('Pumas Cadete', 'María López', 2, 0, 0, 0, 0, 0, 0, 0);

-- Insertar jugadores de prueba

-- Jugadores del Real Madrid FC (equipo_id = 1)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Vinícius', 'Júnior', '12345001', 'Brasileña', 1, 0),
('Karim', 'Benzema', '12345002', 'Francesa', 1, 0),
('Luka', 'Modrić', '12345003', 'Croata', 1, 0),
('Thibaut', 'Courtois', '12345004', 'Belga', 1, 0),
('Éder', 'Militão', '12345005', 'Brasileña', 1, 0),
('Toni', 'Kroos', '12345006', 'Alemana', 1, 0),
('Casemiro', 'Silva', '12345007', 'Brasileña', 1, 0),
('Marco', 'Asensio', '12345008', 'Española', 1, 0),
('Rodrygo', 'Goes', '12345009', 'Brasileña', 1, 0),
('Eduardo', 'Camavinga', '12345010', 'Francesa', 1, 0),
('Dani', 'Carvajal', '12345011', 'Española', 1, 0);

-- Jugadores del FC Barcelona (equipo_id = 2)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Robert', 'Lewandowski', '12345012', 'Polaca', 2, 0),
('Ousmane', 'Dembélé', '12345013', 'Francesa', 2, 0),
('Pedri', 'González', '12345014', 'Española', 2, 0),
('Gavi', 'Páez', '12345015', 'Española', 2, 0),
('Frenkie', 'de Jong', '12345016', 'Holandesa', 2, 0),
('Marc-André', 'ter Stegen', '12345017', 'Alemana', 2, 0),
('Ronald', 'Araújo', '12345018', 'Uruguaya', 2, 0),
('Jules', 'Koundé', '12345019', 'Francesa', 2, 0),
('Jordi', 'Alba', '12345020', 'Española', 2, 0),
('Sergi', 'Roberto', '12345021', 'Española', 2, 0),
('Ansu', 'Fati', '12345022', 'Española', 2, 0);

-- Jugadores del Atlético Madrid (equipo_id = 3)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Antoine', 'Griezmann', '12345023', 'Francesa', 3, 0),
('Álvaro', 'Morata', '12345024', 'Española', 3, 0),
('Koke', 'Resurrección', '12345025', 'Española', 3, 0),
('Jan', 'Oblak', '12345026', 'Eslovena', 3, 0),
('José María', 'Giménez', '12345027', 'Uruguaya', 3, 0),
('Stefan', 'Savić', '12345028', 'Montenegrina', 3, 0),
('Marcos', 'Llorente', '12345029', 'Española', 3, 0),
('Yannick', 'Carrasco', '12345030', 'Belga', 3, 0),
('João', 'Félix', '12345031', 'Portuguesa', 3, 0),
('Rodrigo', 'de Paul', '12345032', 'Argentina', 3, 0),
('Reinildo', 'Mandava', '12345033', 'Mozambiqueña', 3, 0);

-- Jugadores del Valencia CF (equipo_id = 4)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Carlos', 'Soler', '12345034', 'Española', 4, 0),
('Gonçalo', 'Guedes', '12345035', 'Portuguesa', 4, 0),
('José', 'Gayà', '12345036', 'Española', 4, 0),
('Giorgi', 'Mamardashvili', '12345037', 'Georgiana', 4, 0),
('Gabriel', 'Paulista', '12345038', 'Brasileña', 4, 0),
('Hugo', 'Duro', '12345039', 'Española', 4, 0),
('Yunus', 'Musah', '12345040', 'Estadounidense', 4, 0),
('Maximiliano', 'Gómez', '12345041', 'Uruguaya', 4, 0),
('Thierry', 'Correia', '12345042', 'Portuguesa', 4, 0),
('André', 'Almeida', '12345043', 'Portuguesa', 4, 0),
('Samuel', 'Lino', '12345044', 'Brasileña', 4, 0);

-- Insertar enfrentamientos de prueba

-- Enfrentamientos del torneo Liga Premier 2024 (torneo_id = 1)
INSERT INTO enfrentamientos (torneo_id, equipo_local_id, equipo_visitante_id, fecha_hora, cancha, estado, goles_local, goles_visitante) VALUES
(1, 3, 4, '2025-09-13T18:00:00', 'Metropolitano', 'PROGRAMADO', null, null),
(1, 2, 3, '2025-09-14T19:00:00', 'Camp Nou', 'PROGRAMADO', null, null),
(1, 1, 2, '2025-09-20T20:00:00', 'Estadio Santiago Bernabéu', 'PROGRAMADO', null, null),
(1, 1, 3, '2025-09-20T21:00:00', 'Estadio Santiago Bernabéu', 'PROGRAMADO', null, null),
(1, 2, 4, '2025-09-21T19:00:00', 'Camp Nou', 'PROGRAMADO', null, null),
(1, 4, 1, '2025-09-21T18:00:00', 'Mestalla', 'PROGRAMADO', null, null);

-- Enfrentamientos del torneo Copa América Junior (torneo_id = 2)
INSERT INTO enfrentamientos (torneo_id, equipo_local_id, equipo_visitante_id, fecha_hora, cancha, estado, goles_local, goles_visitante) VALUES
(2, 5, 6, '2025-09-20T16:00:00', 'Estadio Juvenil Norte', 'PROGRAMADO', null, null),
(2, 7, 8, '2025-09-20T14:00:00', 'Campo Municipal Sur', 'PROGRAMADO', null, null),
(2, 5, 7, '2025-09-21T16:00:00', 'Estadio Juvenil Norte', 'PROGRAMADO', null, null),
(2, 6, 8, '2025-09-21T14:00:00', 'Coliseo Deportivo', 'PROGRAMADO', null, null),
(2, 5, 8, '2025-09-21T17:00:00', 'Estadio Juvenil Norte', 'PROGRAMADO', null, null),
(2, 6, 7, '2025-09-21T15:00:00', 'Campo Municipal Sur', 'PROGRAMADO', null, null);