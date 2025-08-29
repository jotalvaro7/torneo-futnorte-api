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
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Vinícius', 'Júnior', '12345001', 'Brasileña', 1, 8),
('Karim', 'Benzema', '12345002', 'Francesa', 1, 12),
('Luka', 'Modrić', '12345003', 'Croata', 1, 3),
('Thibaut', 'Courtois', '12345004', 'Belga', 1, 0),
('Éder', 'Militão', '12345005', 'Brasileña', 1, 2),
('Toni', 'Kroos', '12345006', 'Alemana', 1, 1),
('Casemiro', 'Silva', '12345007', 'Brasileña', 1, 4),
('Marco', 'Asensio', '12345008', 'Española', 1, 6),
('Rodrygo', 'Goes', '12345009', 'Brasileña', 1, 5),
('Eduardo', 'Camavinga', '12345010', 'Francesa', 1, 2),
('Dani', 'Carvajal', '12345011', 'Española', 1, 1);

-- Jugadores del FC Barcelona (equipo_id = 2)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Robert', 'Lewandowski', '12345012', 'Polaca', 2, 15),
('Ousmane', 'Dembélé', '12345013', 'Francesa', 2, 7),
('Pedri', 'González', '12345014', 'Española', 2, 4),
('Gavi', 'Páez', '12345015', 'Española', 2, 3),
('Frenkie', 'de Jong', '12345016', 'Holandesa', 2, 2),
('Marc-André', 'ter Stegen', '12345017', 'Alemana', 2, 0),
('Ronald', 'Araújo', '12345018', 'Uruguaya', 2, 1),
('Jules', 'Koundé', '12345019', 'Francesa', 2, 2),
('Jordi', 'Alba', '12345020', 'Española', 2, 1),
('Sergi', 'Roberto', '12345021', 'Española', 2, 0),
('Ansu', 'Fati', '12345022', 'Española', 2, 3);

-- Jugadores del Atlético Madrid (equipo_id = 3)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Antoine', 'Griezmann', '12345023', 'Francesa', 3, 9),
('Álvaro', 'Morata', '12345024', 'Española', 3, 7),
('Koke', 'Resurrección', '12345025', 'Española', 3, 2),
('Jan', 'Oblak', '12345026', 'Eslovena', 3, 0),
('José María', 'Giménez', '12345027', 'Uruguaya', 3, 3),
('Stefan', 'Savić', '12345028', 'Montenegrina', 3, 1),
('Marcos', 'Llorente', '12345029', 'Española', 3, 4),
('Yannick', 'Carrasco', '12345030', 'Belga', 3, 6),
('João', 'Félix', '12345031', 'Portuguesa', 3, 5),
('Rodrigo', 'de Paul', '12345032', 'Argentina', 3, 1),
('Reinildo', 'Mandava', '12345033', 'Mozambiqueña', 3, 0);

-- Jugadores del Valencia CF (equipo_id = 4)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Carlos', 'Soler', '12345034', 'Española', 4, 5),
('Gonçalo', 'Guedes', '12345035', 'Portuguesa', 4, 4),
('José', 'Gayà', '12345036', 'Española', 4, 2),
('Giorgi', 'Mamardashvili', '12345037', 'Georgiana', 4, 0),
('Gabriel', 'Paulista', '12345038', 'Brasileña', 4, 1),
('Hugo', 'Duro', '12345039', 'Española', 4, 6),
('Yunus', 'Musah', '12345040', 'Estadounidense', 4, 2),
('Maximiliano', 'Gómez', '12345041', 'Uruguaya', 4, 3),
('Thierry', 'Correia', '12345042', 'Portuguesa', 4, 1),
('André', 'Almeida', '12345043', 'Portuguesa', 4, 0),
('Samuel', 'Lino', '12345044', 'Brasileña', 4, 2);

-- Jugadores del Sevilla FC (equipo_id = 5)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
('Youssef', 'En-Nesyri', '12345045', 'Marroquí', 5, 4),
('Rafa', 'Mir', '12345046', 'Española', 5, 3),
('Ivan', 'Rakitić', '12345047', 'Croata', 5, 2),
('Yassine', 'Bono', '12345048', 'Marroquí', 5, 0),
('Jules', 'Koundé', '12345049', 'Francesa', 5, 1),
('Diego', 'Carlos', '12345050', 'Brasileña', 5, 2),
('Jesús', 'Navas', '12345051', 'Española', 5, 1),
('Papu', 'Gómez', '12345052', 'Argentina', 5, 3),
('Joan', 'Jordán', '12345053', 'Española', 5, 2),
('Marcos', 'Acuña', '12345054', 'Argentina', 5, 1),
('Óliver', 'Torres', '12345055', 'Española', 5, 1);

-- Jugadores de equipos juveniles (equipos 6-9)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
-- Águilas Juvenil (equipo_id = 6)
('Santiago', 'Rodríguez', '12345056', 'Colombiana', 6, 0),
('Miguel', 'Fernández', '12345057', 'Colombiana', 6, 0),
('Diego', 'López', '12345058', 'Colombiana', 6, 0),
('Carlos', 'Martínez', '12345059', 'Colombiana', 6, 0),
('Andrés', 'García', '12345060', 'Colombiana', 6, 0),
('Luis', 'Hernández', '12345061', 'Colombiana', 6, 0),
('Pablo', 'Sánchez', '12345062', 'Colombiana', 6, 0),
('Juan', 'Pérez', '12345063', 'Colombiana', 6, 0),
('Daniel', 'Ruiz', '12345064', 'Colombiana', 6, 0),
('Alejandro', 'Morales', '12345065', 'Colombiana', 6, 0),
('Ricardo', 'Silva', '12345066', 'Colombiana', 6, 0),

-- Leones Sub-20 (equipo_id = 7)
('Fernando', 'Castro', '12345067', 'Colombiana', 7, 0),
('Manuel', 'Vargas', '12345068', 'Colombiana', 7, 0),
('Sebastián', 'Torres', '12345069', 'Colombiana', 7, 0),
('Nicolás', 'Romero', '12345070', 'Colombiana', 7, 0),
('Gabriel', 'Mendoza', '12345071', 'Colombiana', 7, 0),
('Mateo', 'Jiménez', '12345072', 'Colombiana', 7, 0),
('Emilio', 'Ramos', '12345073', 'Colombiana', 7, 0),
('Joaquín', 'Cruz', '12345074', 'Colombiana', 7, 0),
('Antonio', 'Ortega', '12345075', 'Colombiana', 7, 0),
('Francisco', 'Delgado', '12345076', 'Colombiana', 7, 0),
('Javier', 'Herrera', '12345077', 'Colombiana', 7, 0),

-- Tigres Junior (equipo_id = 8)
('Matías', 'Vega', '12345078', 'Colombiana', 8, 0),
('Gonzalo', 'Núñez', '12345079', 'Colombiana', 8, 0),
('Tomás', 'Aguilar', '12345080', 'Colombiana', 8, 0),
('Felipe', 'Medina', '12345081', 'Colombiana', 8, 0),
('Ignacio', 'Reyes', '12345082', 'Colombiana', 8, 0),
('Maximiliano', 'Guerrero', '12345083', 'Colombiana', 8, 0),
('Benjamín', 'Flores', '12345084', 'Colombiana', 8, 0),
('Valentín', 'Cabrera', '12345085', 'Colombiana', 8, 0),
('Cristian', 'Moreno', '12345086', 'Colombiana', 8, 0),
('Facundo', 'Palacios', '12345087', 'Colombiana', 8, 0),
('Luciano', 'Vásquez', '12345088', 'Colombiana', 8, 0),

-- Pumas Cadete (equipo_id = 9)
('Thiago', 'Contreras', '12345089', 'Colombiana', 9, 0),
('Emiliano', 'Paredes', '12345090', 'Colombiana', 9, 0),
('Bruno', 'Espinoza', '12345091', 'Colombiana', 9, 0),
('Leonardo', 'Campos', '12345092', 'Colombiana', 9, 0),
('Rodrigo', 'Villanueva', '12345093', 'Colombiana', 9, 0),
('Joaquín', 'Ibarra', '12345094', 'Colombiana', 9, 0),
('Agustín', 'Soto', '12345095', 'Colombiana', 9, 0),
('Nicolás', 'Peña', '12345096', 'Colombiana', 9, 0),
('Santiago', 'Molina', '12345097', 'Colombiana', 9, 0),
('Mateo', 'Carrasco', '12345098', 'Colombiana', 9, 0),
('Diego', 'Fuentes', '12345099', 'Colombiana', 9, 0);

-- Jugadores de equipos regionales (equipos 10-13)
INSERT INTO jugadores (nombre, apellido, identificacion, nacionalidad, equipo_id, numero_goles) VALUES
-- Norte United (equipo_id = 10)
('Eduardo', 'Montoya', '12345100', 'Colombiana', 10, 7),
('Ricardo', 'Valdez', '12345101', 'Colombiana', 10, 5),
('Javier', 'Salinas', '12345102', 'Colombiana', 10, 3),
('Sergio', 'Coronado', '12345103', 'Colombiana', 10, 0),
('Héctor', 'Navarro', '12345104', 'Colombiana', 10, 2),
('Armando', 'Lozano', '12345105', 'Colombiana', 10, 4),
('Ramón', 'Castillo', '12345106', 'Colombiana', 10, 1),
('Arturo', 'Sandoval', '12345107', 'Colombiana', 10, 3),
('Omar', 'Guerrero', '12345108', 'Colombiana', 10, 2),
('Raúl', 'Cervantes', '12345109', 'Colombiana', 10, 1),
('Gustavo', 'Morales', '12345110', 'Colombiana', 10, 0),

-- Montaña FC (equipo_id = 11)
('Alberto', 'Quintero', '12345111', 'Colombiana', 11, 4),
('Víctor', 'Domínguez', '12345112', 'Colombiana', 11, 3),
('Enrique', 'Maldonado', '12345113', 'Colombiana', 11, 2),
('Roberto', 'Gallegos', '12345114', 'Colombiana', 11, 0),
('Alfredo', 'Pacheco', '12345115', 'Colombiana', 11, 1),
('Mauricio', 'Estrada', '12345116', 'Colombiana', 11, 5),
('Jorge', 'Velasco', '12345117', 'Colombiana', 11, 2),
('Octavio', 'Ríos', '12345118', 'Colombiana', 11, 3),
('César', 'Cárdenas', '12345119', 'Colombiana', 11, 1),
('Pedro', 'Alvarado', '12345120', 'Colombiana', 11, 1),
('Rubén', 'Figueroa', '12345121', 'Colombiana', 11, 0),

-- Cordillera CF (equipo_id = 12)
('Mario', 'Villalba', '12345122', 'Colombiana', 12, 2),
('Iván', 'Ochoa', '12345123', 'Colombiana', 12, 1),
('Óscar', 'Bermúdez', '12345124', 'Colombiana', 12, 3),
('Hugo', 'Téllez', '12345125', 'Colombiana', 12, 0),
('Adrián', 'Porras', '12345126', 'Colombiana', 12, 1),
('Felipe', 'Aranda', '12345127', 'Colombiana', 12, 2),
('Gonzalo', 'Saldaña', '12345128', 'Colombiana', 12, 1),
('Esteban', 'Cortés', '12345129', 'Colombiana', 12, 4),
('Rodrigo', 'Bueno', '12345130', 'Colombiana', 12, 0),
('Miguel', 'Franco', '12345131', 'Colombiana', 12, 1),
('Alejandro', 'Ávila', '12345132', 'Colombiana', 12, 0),

-- Nevados FC (equipo_id = 13)
('David', 'Espejo', '12345133', 'Colombiana', 13, 1),
('Carlos', 'Montes', '12345134', 'Colombiana', 13, 2),
('Luis', 'Cabral', '12345135', 'Colombiana', 13, 0),
('José', 'Nava', '12345136', 'Colombiana', 13, 0),
('Manuel', 'Esquivel', '12345137', 'Colombiana', 13, 3),
('Arturo', 'Lemus', '12345138', 'Colombiana', 13, 1),
('Fernando', 'Ponce', '12345139', 'Colombiana', 13, 0),
('Antonio', 'Barrera', '12345140', 'Colombiana', 13, 2),
('Gerardo', 'Zamora', '12345141', 'Colombiana', 13, 1),
('Salvador', 'Trejo', '12345142', 'Colombiana', 13, 0),
('Rafael', 'Arroyo', '12345143', 'Colombiana', 13, 0);