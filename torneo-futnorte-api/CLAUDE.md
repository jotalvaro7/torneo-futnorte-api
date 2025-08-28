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

## Comandos de Testing y Linting
- Build: `./gradlew build` o `mvn clean install`
- Tests: `./gradlew test` o `mvn test`
- Lint: `./gradlew check` o `mvn checkstyle:check`