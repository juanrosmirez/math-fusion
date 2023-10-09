# math-fusion

API de Spring Boot para cálculos de números en tiempo real, limitación de velocidad y manejo de errores, con soporte para Docker, base de datos con migraciones en postgresql, cache, retries y documentación Swagger.


## Requerimientos

- Java 11 o superior
- Gradle
- Docker y Docker Compose
- PostgreSQL

## Clonar el Repositorio

```bash
git clone https://github.com/juanrosmirez/math-fusion.git
cd math-fusion
```

## Despliegue del Dockerfile

#### 1. Crear el Build
Para construir el proyecto, utiliza el siguiente comando:

```bash
 ./gradlew build
```

Esto asegurará que todas las dependencias se descarguen y que el proyecto se compile correctamente.

#### 2. Construir las Imágenes Docker y Crear los Contenedores

Utiliza el siguiente comando para construir las imágenes de Docker y crear los contenedores:

```bash
 docker-compose build
```

#### 3. Configurar las Credenciales de la Base de Datos

Abre el archivo docker-compose.yml y configura las credenciales de la base de datos en la sección environment del servicio postgres y app

```
version: '3'

services:
  postgres:
    image: postgres:13
    container_name: postgres
    environment:
      POSTGRES_DB: math_fusion_db
      POSTGRES_USER: [Usuario de la base de datos]
      POSTGRES_PASSWORD: [Contraseña de la base de datos]
    ports:
      - "5432:5432"
    volumes:
      - ./src/main/resources/db/migration/V1__init.sql:/docker-entrypoint-initdb.d/V1__init.sql

  app:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"    
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/math_fusion_db
      SPRING_DATASOURCE_USERNAME: [Usuario de la base de datos]
      SPRING_DATASOURCE_PASSWORD: [Contraseña de la base de datos]
```


#### 4. Iniciar los Contenedores
Finalmente, inicia los contenedores ejecutando el siguiente comando:
```bash
 docker-compose up
```

#### (Opcional)
Si ya tenes configurado el paso **3** del docker-compose.yml. Hay un script llamado `docker-setup.sh` que ejecuta el paso ***1, 2 y 4 en un script***

```
chmod +x docker-setup.sh
./docker-setup.sh 
```

La finalidad de esto es unicamente para la facilidad de uso

## Swagger
### Desde el link provisto se pueden hacer todas las pruebas
La documentación de la API está disponible en http://localhost:8080/swagger-ui.html. Utiliza Swagger para explorar y probar los endpoints.

###

## ⚠ [IMPORTANTE] Funcionalidades 

### Servicio de Suma con Porcentaje
- **Endpoint:** `/api/calculate`
- **Método:** POST
- **Descripción:** Recibe dos números y opcionalmente un percentageType (para poder forzar un error en el servicio externo y probar las distintas funcionalidades solicitadas), los suma y aplica un porcentaje adquirido de un servicio externo.
- **Punto a destacar:** La unica finalidad del `percentageType` es para una cuestión de facilidad para poder probar todos los casos de uso provistos en los enunciados


- #### Ejemplo de body requests ni bien se ejecuta la aplicación

### CASUÍSTICAS

1. Con `percentageType` incorrecto (por ejemplo, `INCORRECT_PERCENTAGE_TYPE`) genera un error en el servicio externo y hace 3 retries, pero como no tiene un ultimo valor almacenado arroja error:
```
{
  "number1": 8,
  "number2": 12,
  "percentageType": "BAD_PERCENTAGE_TYPE"
}
```

2. Sin `percentageType` aplica un 10% por default a la suma de los números:
 ```json
{
  "number1": 5,
  "number2": 10
}
```

3. Con `percentageType` incorrecto (por ejemplo, `INCORRECT_PERCENTAGE_TYPE`) para forzar el error en el servicio externo y ver como funciona la cache implementada por sesión, como la ejecucción del paso anterior fue correcta, hay un valor en la cache por 30 minutos y lo devuelve:
```

{
  "number1": 8,
  "number2": 12,
  "percentageType": "BAD_PERCENTAGE_TYPE"
}
```

4. Con `percentageType` correcto (por ejemplo, `HIGH` o `MEDIUM` o `LOW`):
Esto es meramente para probar un caso de éxito del percentageType que aplica distintos porcentajes
- `HIGH: 100%` 
- `MEDIUM: 50%`
- `LOW: 10%`
- sin `percentageType` por default es `LOW:10%`
```
{
  "number1": 15,
  "number2": 25,
  "percentageType": "HIGH"
}
```
- **Consideraciones:**
  - Puedes proporcionar un tercer parámetro opcional llamado percentageType en el cuerpo de la solicitud. Puede tomar tres valores posibles: LOW, MEDIUM y HIGH. Esta opción se utiliza para simular errores en la API externa. Si se proporciona un valor incorrecto (por ejemplo, MID), se generará un error.
  - El servicio externo puede ser un mock y debe devolver el porcentaje sumado.
  - El porcentaje adquirido del servicio externo se considera constante durante 30 minutos debido a una cache por sesión implementada.
  - Si el servicio externo falla, se devuelve el último valor retornado o un error si no hay valor.
  - Se permite un máximo de 3 reintentos si el servicio falla.

### Historial de Llamadas

- **Endpoint:** `/api/call-history??page=[PAGE]&size[SIZE]`
- **Método:** GET
- **Descripción:** Obtiene el historial de todos los llamados a los endpoints junto con las respuestas en caso de éxito.
- **Consideraciones:**
  - La información se devuelve en formato JSON con paginación.
  - El historial de llamadas no afecta el tiempo de respuesta de la API.
  
  
### Limitación de RPM

- **Consideración:** La API soporta un máximo de 3 solicitudes por minuto (RPM). Si se supera este límite, se devuelve un error HTTP adecuado.

## Ejecucción en localhost

#### 1. Configurar el application.properties y crear la base de datos
- Configurar los environments USERNAME, PASSWORD
- Crear la base de datos


#### 2. Crear el Build
Para construir el proyecto, utiliza el siguiente comando:

```bash
 ./gradlew build
```

#### 3. Ejecutar  el proyecto
Para ejecutar el proyecto, utiliza el siguiente comando:

```bash
 ./gradlew bootRun
```


## Tests
Para poder ejecutar los test hay que escribir el siguiente comando:
```
./gradlew test
````


