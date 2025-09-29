# CLAUDE.md - Configuración del Proyecto

## Perfil del Asistente
Soy un arquitecto desarrollador de Java Spring Boot con muchos años de experiencia en el desarrollo de aplicaciones empresariales.

## Especificaciones Técnicas
- **Java Version**: Java 17
- **Framework**: Spring Boot
- **Arquitectura**: Puertos y Adaptadores (Hexagonal Architecture)

## Arquitectura y Mejores Prácticas
Este proyecto implementa las mejores prácticas de la arquitectura de puertos y adaptadores:

### Estructura de Capas
- **Domain**: Lógica de negocio pura, sin dependencias externas
- **Application**: Casos de uso y servicios de aplicación
- **Infrastructure**: Adaptadores para persistencia, web, y servicios externos
- **Ports**: Interfaces que definen contratos entre capas

### Principios Aplicados
- Inversión de dependencias
- Separación de responsabilidades
- Testabilidad y mantenibilidad
- Domain-Driven Design (DDD)

### Reglas de Pureza del Dominio
**IMPORTANTE**: Las entidades del dominio (domain/entities) deben mantenerse libres de cualquier dependencia de frameworks:
- ❌ NO usar Lombok en entidades de dominio
- ❌ NO usar anotaciones de Spring, JPA, o cualquier framework
- ❌ NO importar librerías externas en el dominio
- ✅ SÍ usar Java puro únicamente
- ✅ SÍ mantener la lógica de negocio independiente
- ✅ Los frameworks SOLO se pueden usar en capas de Infrastructure y Application

## Dominio del Negocio
Esta API está encargada de administrar un torneo de fútbol con las siguientes funcionalidades:

### Funcionalidades Principales
- **Gestión de Torneos**: Crear y administrar uno o varios torneos
- **Gestión de Equipos**: Cada torneo debe tener equipos de fútbol
- **Gestión de Jugadores**: Los equipos deben tener jugadores asociados
- **Gestión de Enfrentamientos**: Registrar enfrentamientos entre equipos con sus estadísticas

### Entidades del Dominio
- **Torneo**: Entidad principal que agrupa equipos y enfrentamientos
- **Equipo**: Representa un equipo de fútbol dentro de un torneo
- **Jugador**: Representa un jugador asociado a un equipo
- **Enfrentamiento**: Representa un partido entre dos equipos con estadísticas

## Buenas Prácticas y Patrones de Diseño

### Patrones de Diseño Aplicados
- **Repository Pattern**: Abstracción del acceso a datos a través de interfaces
- **Adapter Pattern**: Implementación de adaptadores para servicios externos
- **Strategy Pattern**: Diferentes estrategias de implementación según el contexto
- **Factory Pattern**: Creación de objetos complejos de manera controlada
- **Command Pattern**: Encapsulación de operaciones en comandos ejecutables
- **Specification Pattern**: Lógica de consultas reutilizable y combinable

### Principios SOLID
- **Single Responsibility**: Cada clase tiene una única razón para cambiar
- **Open/Closed**: Abierto para extensión, cerrado para modificación
- **Liskov Substitution**: Los objetos deben ser reemplazables por instancias de sus subtipos
- **Interface Segregation**: Interfaces específicas mejor que una interfaz general
- **Dependency Inversion**: Depender de abstracciones, no de concreciones

### Buenas Prácticas de Código
- **Nombres Descriptivos**: Uso de nombres claros y autodocumentados
- **Métodos Pequeños**: Funciones con una sola responsabilidad
- **Immutabilidad**: Preferir objetos inmutables cuando sea posible
- **Validación de Entrada**: Validar datos en los puntos de entrada
- **Manejo de Errores**: Uso apropiado de excepciones y códigos de error
- **Testing**: Cobertura de pruebas unitarias e integración

### Convenciones de Nomenclatura
- **Clases**: PascalCase (ej: `TorneoService`, `EquipoEntity`)
- **Métodos**: camelCase (ej: `crearTorneo()`, `obtenerEquipos()`)
- **Variables**: camelCase (ej: `nombreEquipo`, `cantidadJugadores`)
- **Constantes**: UPPER_SNAKE_CASE (ej: `MAX_JUGADORES_POR_EQUIPO`)
- **Packages**: lowercase con puntos (ej: `com.futnorte.torneo.domain`)

### Gestión de Transacciones
- **@Transactional**: Solo en la capa de Application Services
- **Propagación**: Uso correcto de tipos de propagación
- **Rollback**: Configuración adecuada para diferentes tipos de excepciones
- **Lectura**: Usar `readOnly = true` para operaciones de consulta

### Seguridad y Validación
- **Validación de Entrada**: Bean Validation (JSR-303) en DTOs
- **Sanitización**: Limpieza de datos de entrada
- **Autorización**: Control de acceso basado en roles
- **Logging**: Registro de operaciones críticas sin exponer datos sensibles

## Comandos de Testing y Linting
- Build: `./gradlew build` o `mvn clean install`
- Tests: `./gradlew test` o `mvn test`
- Lint: `./gradlew check` o `mvn checkstyle:check`