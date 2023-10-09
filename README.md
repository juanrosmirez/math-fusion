# math-fusion
Spring Boot API for real-time number calculations, rate limiting, and error handling, with Docker support and Swagger docs.

# API REST en Spring Boot

Esta es una API REST desarrollada en Spring Boot que cumple con los siguientes requerimientos:

## Requerimientos

- Java 11 o superior
- Gradle
- Docker y Docker Compose
- PostgreSQL

## Descripción

Esta API proporciona una serie de servicios que permiten realizar cálculos matemáticos y gestionar un historial de llamadas.

## Funcionalidades

### Servicio de Suma con Porcentaje

- **Endpoint:** `/api/sum-with-percentage`
- **Método:** POST
- **Descripción:** Recibe dos números, los suma y aplica un porcentaje adquirido de un servicio externo.
- **Consideraciones:**
  - El servicio externo puede ser un mock y debe devolver el porcentaje sumado.
  - El porcentaje adquirido del servicio externo se considera constante durante 30 minutos.
  - Si el servicio externo falla, se devuelve el último valor retornado o un error si no hay valor.
  - Se permite un máximo de 3 reintentos si el servicio falla.

### Historial de Llamadas

- **Endpoint:** `/api/call-history`
- **Método:** GET
- **Descripción:** Obtiene el historial de todos los llamados a los endpoints junto con las respuestas en caso de éxito.
- **Consideraciones:**
  - La información se devuelve en formato JSON con paginación.
  - El historial de llamadas no afecta el tiempo de respuesta de la API.
  
### Limitación de RPM

- **Consideración:** La API soporta un máximo de 3 solicitudes por minuto (RPM). Si se supera este límite, se devuelve un error HTTP adecuado.

### Swagger

Documentación y Pruebas
Swagger
La documentación de la API está disponible en http://localhost:8080/swagger-ui.html. Utiliza Swagger para explorar y probar los endpoints.

## Desarrollo de la API

### Clonar el Repositorio

```bash
git clone https://github.com/tuusuario/tu-api-spring-boot.git
cd tu-api-spring-boot