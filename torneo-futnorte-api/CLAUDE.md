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

## Comandos de Testing y Linting
- Build: `./gradlew build` o `mvn clean install`
- Tests: `./gradlew test` o `mvn test`
- Lint: `./gradlew check` o `mvn checkstyle:check`