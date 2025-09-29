# 📋 Documentación de API - Schema First

## 🎯 **Enfoque Adoptado: Schema-First**

Esta API utiliza el enfoque **Schema-First** manteniendo el código del dominio completamente limpio de anotaciones externas, preservando los principios de **Arquitectura Hexagonal**.

## 📄 **Especificación OpenAPI**

### 📍 Archivo Principal
```
src/main/resources/static/openapi.yaml
```

### 🔗 URLs de Acceso

**Especificación YAML:**
```
http://localhost:8080/api/openapi.yaml
```

**JSON (generado automáticamente):**
```
http://localhost:8080/api/openapi.json
```

## 🛠️ **Herramientas Recomendadas**

### 1. **Swagger Editor (Online)**
```
https://editor.swagger.io/
```
- Pega el contenido del `openapi.yaml`
- Visualización inmediata
- Validación automática
- Generación de código cliente

### 2. **Swagger UI (Docker)**
```bash
docker run -p 8081:8080 -e SWAGGER_JSON=/openapi.yaml \
  -v $(pwd)/src/main/resources/static/openapi.yaml:/openapi.yaml \
  swaggerapi/swagger-ui
```
Acceder: `http://localhost:8081`

### 3. **ReDoc (Docker)**
```bash
docker run -it --rm -p 8082:80 \
  -v $(pwd)/src/main/resources/static/openapi.yaml:/usr/share/nginx/html/openapi.yaml \
  redocly/redoc
```
Acceder: `http://localhost:8082`

### 4. **VSCode Extensions**
- **Swagger Viewer**: `Arjun.swagger-viewer`
- **OpenAPI (Swagger) Editor**: `42Crunch.vscode-openapi`
- **YAML**: `redhat.vscode-yaml`

## 🏗️ **Características de la Especificación**

### ✅ **Completamente Documentado**
- **Información del Proyecto**: Título, descripción detallada, versión, contacto, licencia
- **Servidores**: Desarrollo y producción
- **Endpoints**: Todos los endpoints de `TorneoController` 
- **Modelos**: `Torneo`, `EstadoTorneo`, `ErrorResponse`
- **Códigos de Respuesta**: Documentación completa con ejemplos
- **Reglas de Negocio**: Explicación clara de estados y transiciones

### ✅ **Integración con Sistema de Errores**
- **Códigos de Error Específicos**: `ENTITY_NOT_FOUND`, `DUPLICATE_ENTITY`, etc.
- **Trace IDs**: Para trazabilidad completa
- **Ejemplos Reales**: Basados en el sistema de excepciones implementado
- **Respuestas Estándar**: Componentes reutilizables

### ✅ **Ejemplos Prácticos**
- Requests de ejemplo para cada endpoint
- Responses de ejemplo con datos realistas
- Casos de error con trace IDs
- Validaciones y reglas de negocio

## 🔄 **Flujo de Desarrollo**

### **1. Modificar la API**
- Actualizar primero `openapi.yaml`
- Validar la especificación
- Implementar en el código

### **2. Generar Documentación**
- Usar Swagger Editor para visualizar
- Exportar documentación estática
- Compartir con el equipo

### **3. Generar Código Cliente**
- Usar herramientas como `openapi-generator`
- Generar SDKs para diferentes lenguajes
- Mantener consistencia entre cliente y servidor

## 💡 **Ventajas de este Enfoque**

### ✅ **Código Limpio**
- Dominio puro sin dependencias externas
- Mantenimiento de principios arquitectónicos
- Sin invasión de anotaciones

### ✅ **Documentación Como Fuente de Verdad**
- Especificación independiente del código
- Facilita el desarrollo contract-first
- Documentación siempre actualizada

### ✅ **Flexibilidad**
- Cambios rápidos en documentación
- Múltiples herramientas de visualización
- Generación de código automática

### ✅ **Colaboración Mejorada**
- Frontend puede trabajar con la especificación
- Testing automático basado en contratos
- Validación independiente

## 🚀 **Comandos Rápidos**

### **Validar Especificación**
```bash
# Con swagger-codegen
swagger-codegen validate -i src/main/resources/static/openapi.yaml

# Con openapi-generator
openapi-generator validate -i src/main/resources/static/openapi.yaml
```

### **Generar Documentación HTML**
```bash
# Con redoc-cli
redoc-cli build src/main/resources/static/openapi.yaml --output docs/api.html

# Con swagger-codegen
swagger-codegen generate -i src/main/resources/static/openapi.yaml -l html2 -o docs/
```

### **Generar Cliente JavaScript**
```bash
openapi-generator generate \
  -i src/main/resources/static/openapi.yaml \
  -g javascript \
  -o clients/javascript/
```

## 📖 **Estructura del YAML**

```yaml
openapi: 3.0.3
info: # Información del proyecto
servers: # Servidores disponibles  
paths: # Endpoints documentados
  /torneos: # Operaciones CRUD
  /torneos/{id}: # Operaciones específicas
  /torneos/{id}/iniciar: # Operaciones de estado
components:
  schemas: # Modelos de datos
  responses: # Respuestas estándar
  parameters: # Parámetros reutilizables
tags: # Agrupación y documentación
```

Este enfoque mantiene la **pureza arquitectónica** mientras proporciona una documentación de API de clase empresarial.