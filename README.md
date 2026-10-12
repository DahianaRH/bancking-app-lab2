# Aplicación Bancaria

[![CI/CD Pipeline - Aplicacion Bancaria](https://github.com/DahianaRH/bancking-app-lab2/actions/workflows/build.yml/badge.svg)](https://github.com/DahianaRH/bancking-app-lab2/actions/workflows/build.yml)

Aplicación bancaria desarrollada con **Java 17, Spring Boot y Maven**, utilizada para practicar pruebas de software, integración continua y despliegue continuo (CI/CD).

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
- GitHub Actions para integración continua (CI)

## Estructura del proyecto

- `src/main/java`: código fuente de la aplicación, incluidos controladores, servicios, entidades, repositorios y DTO.
- `src/main/resources`: configuración de la aplicación.
- `src/test/java`: pruebas automatizadas.
- `.github/workflows/build.yml`: configuración del pipeline de GitHub Actions.
- `pom.xml`: dependencias y configuración de compilación, pruebas y cobertura.

## Requisitos previos

- Java 17 o compatible con la versión configurada en Maven.
- Maven instalado, o Maven Wrapper si está disponible en el repositorio.
- MySQL configurado con una base de datos accesible para la aplicación.

## Cómo ejecutar la aplicación

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/USUARIO/REPOSITORIO.git
   cd REPOSITORIO
   ```

2. Configurar la conexión a MySQL en `src/main/resources/application.properties`.

3. Compilar y ejecutar las pruebas:

   ```bash
   mvn clean verify
   ```

4. Iniciar la aplicación:

   ```bash
   mvn spring-boot:run
   ```

La API estará disponible, por defecto, en `http://localhost:8080`.

## Cómo ejecutar las pruebas

Para compilar el proyecto y ejecutar las pruebas automatizadas:

```bash
mvn clean verify
```

El mismo comando se ejecuta automáticamente en GitHub Actions cuando se envían cambios a la rama `main` o se abre un pull request dirigido a ella.

## Cómo consultar la cobertura de código

JaCoCo está configurado en Maven para generar el informe de cobertura durante la fase `verify`.

Ejecutar:

```bash
mvn clean verify
```

Después, consultar el informe HTML en:

```text
target/site/jacoco/index.html
```

Si el informe se genera correctamente, abre ese archivo en un navegador para revisar la cobertura de las clases y los métodos ejecutados por las pruebas.

## Integración continua

El proyecto utiliza **GitHub Actions** para automatizar la compilación y ejecución de pruebas.

El flujo de trabajo se encuentra en:

```text
.github/workflows/build.yml
```

El pipeline se ejecuta al enviar cambios a `main`, al abrir pull requests dirigidos a `main` y también puede iniciarse manualmente desde GitHub Actions.

## Estado del proyecto

El pipeline de integración continua está configurado y su ejecución se puede consultar en la pestaña **Actions** del repositorio.

Los análisis de calidad con SonarCloud y el despliegue en la nube se documentarán cuando estén configurados.
