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

-- Insertar jugadores de prueba

-- Jugadores del Real Madrid FC (equipo_id = 1)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
('Vinícius', 'Júnior', '12345001', 1, 8),
('Karim', 'Benzema', '12345002', 1, 12),
('Luka', 'Modrić', '12345003', 1, 3),
('Thibaut', 'Courtois', '12345004', 1, 0),
('Éder', 'Militão', '12345005', 1, 2),
('Toni', 'Kroos', '12345006', 1, 1),
('Casemiro', 'Silva', '12345007', 1, 4),
('Marco', 'Asensio', '12345008', 1, 6),
('Rodrygo', 'Goes', '12345009', 1, 5),
('Eduardo', 'Camavinga', '12345010', 1, 2),
('Dani', 'Carvajal', '12345011', 1, 1);

-- Jugadores del FC Barcelona (equipo_id = 2)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
('Robert', 'Lewandowski', '12345012', 2, 15),
('Ousmane', 'Dembélé', '12345013', 2, 7),
('Pedri', 'González', '12345014', 2, 4),
('Gavi', 'Páez', '12345015', 2, 3),
('Frenkie', 'de Jong', '12345016', 2, 2),
('Marc-André', 'ter Stegen', '12345017', 2, 0),
('Ronald', 'Araújo', '12345018', 2, 1),
('Jules', 'Koundé', '12345019', 2, 2),
('Jordi', 'Alba', '12345020', 2, 1),
('Sergi', 'Roberto', '12345021', 2, 0),
('Ansu', 'Fati', '12345022', 2, 3);

-- Jugadores del Atlético Madrid (equipo_id = 3)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
('Antoine', 'Griezmann', '12345023', 3, 9),
('Álvaro', 'Morata', '12345024', 3, 7),
('Koke', 'Resurrección', '12345025', 3, 2),
('Jan', 'Oblak', '12345026', 3, 0),
('José María', 'Giménez', '12345027', 3, 3),
('Stefan', 'Savić', '12345028', 3, 1),
('Marcos', 'Llorente', '12345029', 3, 4),
('Yannick', 'Carrasco', '12345030', 3, 6),
('João', 'Félix', '12345031', 3, 5),
('Rodrigo', 'de Paul', '12345032', 3, 1),
('Reinildo', 'Mandava', '12345033', 3, 0);

-- Jugadores del Valencia CF (equipo_id = 4)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
('Carlos', 'Soler', '12345034', 4, 5),
('Gonçalo', 'Guedes', '12345035', 4, 4),
('José', 'Gayà', '12345036', 4, 2),
('Giorgi', 'Mamardashvili', '12345037', 4, 0),
('Gabriel', 'Paulista', '12345038', 4, 1),
('Hugo', 'Duro', '12345039', 4, 6),
('Yunus', 'Musah', '12345040', 4, 2),
('Maximiliano', 'Gómez', '12345041', 4, 3),
('Thierry', 'Correia', '12345042', 4, 1),
('André', 'Almeida', '12345043', 4, 0),
('Samuel', 'Lino', '12345044', 4, 2);

-- Jugadores del Sevilla FC (equipo_id = 5)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
('Youssef', 'En-Nesyri', '12345045', 5, 4),
('Rafa', 'Mir', '12345046', 5, 3),
('Ivan', 'Rakitić', '12345047', 5, 2),
('Yassine', 'Bono', '12345048', 5, 0),
('Jules', 'Koundé', '12345049', 5, 1),
('Diego', 'Carlos', '12345050', 5, 2),
('Jesús', 'Navas', '12345051', 5, 1),
('Papu', 'Gómez', '12345052', 5, 3),
('Joan', 'Jordán', '12345053', 5, 2),
('Marcos', 'Acuña', '12345054', 5, 1),
('Óliver', 'Torres', '12345055', 5, 1);

-- Jugadores de equipos juveniles (equipos 6-9)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
-- Águilas Juvenil (equipo_id = 6)
('Santiago', 'Rodríguez', '12345056', 6, 0),
('Miguel', 'Fernández', '12345057', 6, 0),
('Diego', 'López', '12345058', 6, 0),
('Carlos', 'Martínez', '12345059', 6, 0),
('Andrés', 'García', '12345060', 6, 0),
('Luis', 'Hernández', '12345061', 6, 0),
('Pablo', 'Sánchez', '12345062', 6, 0),
('Juan', 'Pérez', '12345063', 6, 0),
('Daniel', 'Ruiz', '12345064', 6, 0),
('Alejandro', 'Morales', '12345065', 6, 0),
('Ricardo', 'Silva', '12345066', 6, 0),

-- Leones Sub-20 (equipo_id = 7)
('Fernando', 'Castro', '12345067', 7, 0),
('Manuel', 'Vargas', '12345068', 7, 0),
('Sebastián', 'Torres', '12345069', 7, 0),
('Nicolás', 'Romero', '12345070', 7, 0),
('Gabriel', 'Mendoza', '12345071', 7, 0),
('Mateo', 'Jiménez', '12345072', 7, 0),
('Emilio', 'Ramos', '12345073', 7, 0),
('Joaquín', 'Cruz', '12345074', 7, 0),
('Antonio', 'Ortega', '12345075', 7, 0),
('Francisco', 'Delgado', '12345076', 7, 0),
('Javier', 'Herrera', '12345077', 7, 0),

-- Tigres Junior (equipo_id = 8)
('Matías', 'Vega', '12345078', 8, 0),
('Gonzalo', 'Núñez', '12345079', 8, 0),
('Tomás', 'Aguilar', '12345080', 8, 0),
('Felipe', 'Medina', '12345081', 8, 0),
('Ignacio', 'Reyes', '12345082', 8, 0),
('Maximiliano', 'Guerrero', '12345083', 8, 0),
('Benjamín', 'Flores', '12345084', 8, 0),
('Valentín', 'Cabrera', '12345085', 8, 0),
('Cristian', 'Moreno', '12345086', 8, 0),
('Facundo', 'Palacios', '12345087', 8, 0),
('Luciano', 'Vásquez', '12345088', 8, 0),

-- Pumas Cadete (equipo_id = 9)
('Thiago', 'Contreras', '12345089', 9, 0),
('Emiliano', 'Paredes', '12345090', 9, 0),
('Bruno', 'Espinoza', '12345091', 9, 0),
('Leonardo', 'Campos', '12345092', 9, 0),
('Rodrigo', 'Villanueva', '12345093', 9, 0),
('Joaquín', 'Ibarra', '12345094', 9, 0),
('Agustín', 'Soto', '12345095', 9, 0),
('Nicolás', 'Peña', '12345096', 9, 0),
('Santiago', 'Molina', '12345097', 9, 0),
('Mateo', 'Carrasco', '12345098', 9, 0),
('Diego', 'Fuentes', '12345099', 9, 0);

-- Jugadores de equipos regionales (equipos 10-13)
INSERT INTO jugadores (nombre, apellido, identificacion, equipo_id, numero_goles) VALUES
-- Norte United (equipo_id = 10)
('Eduardo', 'Montoya', '12345100', 10, 7),
('Ricardo', 'Valdez', '12345101', 10, 5),
('Javier', 'Salinas', '12345102', 10, 3),
('Sergio', 'Coronado', '12345103', 10, 0),
('Héctor', 'Navarro', '12345104', 10, 2),
('Armando', 'Lozano', '12345105', 10, 4),
('Ramón', 'Castillo', '12345106', 10, 1),
('Arturo', 'Sandoval', '12345107', 10, 3),
('Omar', 'Guerrero', '12345108', 10, 2),
('Raúl', 'Cervantes', '12345109', 10, 1),
('Gustavo', 'Morales', '12345110', 10, 0),

-- Montaña FC (equipo_id = 11)
('Alberto', 'Quintero', '12345111', 11, 4),
('Víctor', 'Domínguez', '12345112', 11, 3),
('Enrique', 'Maldonado', '12345113', 11, 2),
('Roberto', 'Gallegos', '12345114', 11, 0),
('Alfredo', 'Pacheco', '12345115', 11, 1),
('Mauricio', 'Estrada', '12345116', 11, 5),
('Jorge', 'Velasco', '12345117', 11, 2),
('Octavio', 'Ríos', '12345118', 11, 3),
('César', 'Cárdenas', '12345119', 11, 1),
('Pedro', 'Alvarado', '12345120', 11, 1),
('Rubén', 'Figueroa', '12345121', 11, 0),

-- Cordillera CF (equipo_id = 12)
('Mario', 'Villalba', '12345122', 12, 2),
('Iván', 'Ochoa', '12345123', 12, 1),
('Óscar', 'Bermúdez', '12345124', 12, 3),
('Hugo', 'Téllez', '12345125', 12, 0),
('Adrián', 'Porras', '12345126', 12, 1),
('Felipe', 'Aranda', '12345127', 12, 2),
('Gonzalo', 'Saldaña', '12345128', 12, 1),
('Esteban', 'Cortés', '12345129', 12, 4),
('Rodrigo', 'Bueno', '12345130', 12, 0),
('Miguel', 'Franco', '12345131', 12, 1),
('Alejandro', 'Ávila', '12345132', 12, 0),

-- Nevados FC (equipo_id = 13)
('David', 'Espejo', '12345133', 13, 1),
('Carlos', 'Montes', '12345134', 13, 2),
('Luis', 'Cabral', '12345135', 13, 0),
('José', 'Nava', '12345136', 13, 0),
('Manuel', 'Esquivel', '12345137', 13, 3),
('Arturo', 'Lemus', '12345138', 13, 1),
('Fernando', 'Ponce', '12345139', 13, 0),
('Antonio', 'Barrera', '12345140', 13, 2),
('Gerardo', 'Zamora', '12345141', 13, 1),
('Salvador', 'Trejo', '12345142', 13, 0),
('Rafael', 'Arroyo', '12345143', 13, 0);