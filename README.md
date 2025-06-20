# 🏛️ Sistema de Gestión de Trámites - Backend SIC

Este es el backend del sistema **SIC** (Sistema de Información de Trámites) desarrollado en **Java con Spring Boot**, diseñado para gestionar personas, empleados, tipos de documentos y trámites administrativos. Incluye integración con Keycloak para autenticación y HashiCorp Vault para gestión de secretos.

---

## 🚀 Tecnologías principales

| Tecnología       | Versión        | Descripción                                       |
|------------------|----------------|---------------------------------------------------|
| Java             | 17             | Lenguaje de programación backend                 |
| Spring Boot      | 3.1.x          | Framework de desarrollo Java                     |
| Spring Cloud     | Hoxton / 2023+ | Compatibilidad con microservicios                |
| PostgreSQL       | 16             | Base de datos relacional                         |
| Keycloak         | 25.x           | Sistema de autenticación y gestión de usuarios   |
| Vault            | 1.15           | Gestión de secretos                              |
| Docker & Compose | Latest         | Contenerización y orquestación local             |
| Gradle           | Kotlin DSL     | Sistema de construcción                          |

---

## 📁 Estructura del proyecto

```plaintext
/Sic/
│
├── docker-compose/
│   ├── docker-compose.yml              # Orquestación de servicios
│   ├── init.sql                        # Script para inicializar la base de datos PostgreSQL
│   ├── vault-secrets.sh                # Script para cargar secretos en Vault
│   ├── keycloak-init/                  # Script de inicialización de realm y cliente Keycloak
│
├── backend/
│   ├── Dockerfile                      # Imagen de backend con Gradle
│   └── src/                            # Código fuente Java
│
├── frontend/
│   ├── Dockerfile                      # Imagen de frontend (en construcción)
```

---

## 🧠 Funcionalidad del Backend

### 🔒 Autenticación
- Gestionada con Keycloak (`SicTest` realm).
- Los endpoints están protegidos mediante JWT y OAuth2 Resource Server (`spring.security.oauth2.resourceserver.jwt.issuer-uri`).

### 📊 Entidades Principales

| Entidad         | Relación                                         |
|------------------|--------------------------------------------------|
| Person           | Tiene un `DocumentType` y puede ser `Employee`  |
| Employee         | Extiende a una `Person`                         |
| DocumentType     | Asociado a cada `Person`                        |
| Procedure        | Asociado a un `submittedBy` (Person) y un `receivedBy` (Employee) |

### 🔄 CRUD API

Para cada entidad se ofrecen endpoints RESTful con:
- GET / GET by ID
- POST
- PUT
- DELETE

Las respuestas están encapsuladas en un DTO genérico: `DefaultResponse<T>`.

---

## 🔐 Vault – Gestión de Secretos

Vault se inicia en modo desarrollo (`vault server -dev`) y un script (`vault-secrets.sh`) carga los valores como:

```json
kv/applications/local/TestSic
{
  "spring.datasource.url": "jdbc:postgresql://...",
  "spring.datasource.username": "postgres",
  "spring.datasource.password": "123456",
  ...
}
```

---

## 🧪 Postman

Se proveen colecciones Postman para probar:
- `/api/sic/persons`
- `/api/sic/employees`
- `/api/sic/document-types`
- `/api/sic/procedures`

Cada operación incluye header `Authorization: Bearer <token>` obtenido de Keycloak.

---

## 🐳 Docker Compose

Servicios:

```yaml
services:
  postgres:
  keycloak:
  vault:
  backend:
  frontend:
```

Para levantar todo el entorno:

```bash
cd Sic/docker-compose
docker compose up --build
```

---

## ✅ Consideraciones técnicas

- Se utiliza `ObjectMapper` para mapear entre entidades y DTOs (evitando recursividad infinita).
- Uso de `@Builder`, `@Data` y `@NoArgsConstructor/@AllArgsConstructor` de Lombok.
- Estructura limpia con separación entre `entity`, `repository`, `service`, `controller` y `dto`.
- Controladores diseñados con principios SOLID y manejo robusto de excepciones.
- Controladores REST están bajo prefijo `/api/sic`.

---

## 📌 Notas adicionales

- El sistema requiere que las entidades relacionadas (por ejemplo, `Person`, `Employee`) estén previamente creadas para asociarlas correctamente.
- Se implementa compatibilidad con versiones de Spring Cloud mediante `spring.cloud.compatibility-verifier.enabled=false` si es necesario.
- Asegúrese de usar versiones compatibles de Spring Boot y Spring Cloud (consultar [release train](https://spring.io/projects/spring-cloud#overview)).

---

## 📫 Contacto del autor

Desarrollado por [Ernesto Luis Mosquera Heredia](https://github.com/emosquerafs)  
Repositorio del backend: [github.com/emosquerafs/backendtest-sic](https://github.com/emosquerafs/backendtest-sic)