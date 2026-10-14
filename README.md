# Aplicación Bancaria

[![CI/CD Pipeline - Aplicacion Bancaria](https://github.com/DahianaRH/bancking-app-lab2/actions/workflows/build.yml/badge.svg)](https://github.com/DahianaRH/bancking-app-lab2/actions/workflows/build.yml)
[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=coverage)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Maintainability issues](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=software_quality_maintainability_issues)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Reliability issues](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=software_quality_reliability_issues)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Security issues](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=software_quality_security_issues)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DahianaRH_bancking-app-lab2&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=DahianaRH_bancking-app-lab2)

Aplicación bancaria desarrollada con **Java 17, Spring Boot y Maven**, utilizada para practicar pruebas de software, integración continua, análisis de calidad y despliegue continuo (CI/CD).
El proyecto implementa una API REST que permite consultar clientes y gestionar transacciones bancarias. El backend se despliega en Render, utiliza una base de datos MySQL alojada en Aiven y emplea GitHub Actions para automatizar las tareas de construcción y validación del proyecto.

## Funcionalidades

La aplicación ofrece las siguientes operaciones:

- **Health check:** comprobar que la API responde correctamente mediante `GET /`.
- **Versión de la aplicación:** consultar el nombre y la versión mediante `GET /version`.
- **Consultar clientes:** obtener la lista de clientes mediante `GET /api/customers`.
- **Consultar un cliente:** buscar un cliente por su identificador mediante `GET /api/customers/{id}`.
- **Crear clientes:** registrar un cliente mediante `POST /api/customers`.
- **Consultar transacciones:** consultar transacciones asociadas a un número de cuenta mediante `GET /api/transactions`.
- **Realizar transferencias:** registrar transferencias entre cuentas mediante `POST /api/transactions`.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- MySQL
- JUnit 5 y Spring Boot Test
- JaCoCo para medir la cobertura de código
- GitHub Actions para integración continua y automatización del pipeline.
- SonarCloud para análisis de calidad, si está habilitado en el pipeline.
- Docker para empaquetar y ejecutar la aplicación.
- Docker Hub para publicar la imagen de la aplicación.
- Render para desplegar el backend.
- Aiven para alojar la base de datos MySQL.

## Estructura del proyecto

- `src/main/java`: código fuente de la aplicación, incluidos controladores, servicios, entidades, repositorios y DTO.
- `src/main/resources`: configuración de la aplicación.
- `src/test/java`: pruebas automatizadas.
- `.github/workflows/build.yml`: configuración del pipeline de GitHub Actions.
- `pom.xml`: dependencias y configuración de compilación, pruebas y cobertura.
- `Dockerfile`: construcción de la imagen Docker.

## Requisitos previos

- Java 17 o compatible con la versión configurada en Maven.
- Maven instalado, o Maven Wrapper si está disponible en el repositorio.
- MySQL configurado con una base de datos accesible para la aplicación.

## Cómo ejecutar la aplicación

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/DahianaRH/bancking-app-lab2.git
   cd bancking-app-lab2
   ```

2. Configuración de la base de datos: 
La aplicación utiliza MySQL y obtiene la configuración de conexión mediante variables de entorno. 
Para el desarrollo local se pueden conservar valores predeterminados en application.properties.

En producción, Render utiliza las credenciales del servicio MySQL de Aiven:
- Host y puerto proporcionados por Aiven.
- Base de datos configurada en Aiven.
- Usuario y contraseña configurados en Aiven.
- Conexión SSL obligatoria.

3. Ejecutar la aplicación localmente

- Compilar y ejecutar las pruebas:
   ```bash
   mvn clean verify
   ```

- Iniciar la aplicación:

   ```bash
   mvn spring-boot:run
   ```
La API estará disponible, por defecto, en `http://localhost:8080`.

## Cómo ejecutar las pruebas

Para compilar el proyecto y ejecutar las pruebas automatizadas:

```bash
mvn clean verify
```
El pipeline de GitHub Actions ejecuta las comprobaciones configuradas para validar los cambios antes de integrarlos en la rama principal.

JaCoCo genera el informe de cobertura durante la fase verify. El informe HTML se encuentra en:

```text
target/site/jacoco/index.html
```

## Integración continua

El flujo de trabajo se encuentra en:

```text
.github/workflows/build.yml
```
El flujo de trabajo contempla las siguientes actividades, de acuerdo con la configuración del pipeline:

1. Descargar el código del repositorio.
2. Configurar Java 17.
3. Ejecutar las pruebas y la verificación con Maven.
4. Realizar el análisis de calidad configurado.
5. Construir el artefacto JAR.
6. Construir y publicar la imagen Docker en Docker Hub.
7. Solicitar el despliegue en Render cuando esté configurada la integración mediante Deploy Hook.

Las ejecuciones se pueden consultar en la pestaña Actions del repositorio.

## Despliegue en la nube
### Backend en Render

URL pública: https://aplicacion-bancaria-latest.onrender.com

El backend se ejecuta como un servicio web en Render utilizando una imagen Docker publicada en Docker Hub.

### Base de datos MySQL en Aiven
La base de datos se encuentra en Aiven y se conecta al backend mediante variables de entorno configuradas en Render. La conexión utiliza SSL.