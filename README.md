# Sistema de Gestión de Proyectos Académicos

## Descripción
El **Sistema de Gestión de Proyectos Académicos para Empresas** es una aplicación de escritorio diseñada para fortalecer la colaboración entre universidades y el sector empresarial. 
Su objetivo principal es facilitar la gestión y asignación de proyectos empresariales a estudiantes de últimos semestres, permitiéndoles desarrollar soluciones de software reales como parte de sus prácticas profesionales, pasantías o proyectos académicos.

## Arquitectura
El sistema está basado en una **arquitectura de microservicios** y una **arquitectura hexagonal** bajo la filosofia del Domain Driver Desing,
donde cada microservicio es independiente y colabora con los demás a través de REST y/o mensajería asincrónica. 

### Tecnologías Principales
- **Framework:** Spring Boot
- **Persistencia:** JPA/Hibernate, PostgreSQL
- **Comunicación entre servicios:** REST/Mensajería Asincrónica
- **Servicio de mensajeria:** RabbitMQ
- **Sistema de gestión de identidad y acceso:** KeyCloak, JWT
- **Organización del código:**
  - Paquetes por responsabilidad: `Controller`, `Service`, `Repository`, `Entity`, `DTO`, etc.

## Microservicios

### Microservicio User
- Maneja el inicio de sesión, registro de usuarios, autenticación y autorización de usuarios.

### Microservicio Empresa
- Gestión de las necesidades de las empresas:
  - Publicación y gestión de proyectos.
  - Notificación al coordinador sobre un nuevo proyecto.

### Microservicio Estudiante
- Maneja la lógica relacionada con los estudiantes:
  - Consulta de proyectos aceptados.
  - Postulación a proyectos.
  - Notificación al coordinador de la postulación a un proyecto.

### Microservicio Coordinador
- Da soporte a los coordinadores:
  - Visualización de todos los proyectos.
  - Evaluación de postulaciones y aprobación/rechazo.
  - Notificación a empresas sobre el estado de los proyectos.

## Requisitos

### Funcionales
- Registro de empresas con información detallada.
- Registro de estudiantes con información detallada.
- Publicación de proyectos por parte de las empresas.
- Evaluación y gestión de proyectos por parte del coordinador.
- Visualización y postulación de proyectos por los estudiantes.
- Inicio de sesión para los distintos usuarios.

### No Funcionales
- Escalabilidad: Diseñado para soportar múltiples empresas y proyectos.
- Mantenimiento: Código bien documentado y modular para facilitar futuras mejoras y mantenimiento.
- Usabilidad: Interfaz intuitiva y fácil de usar.
- Seguridad y privacidad: Implementación de autenticación segura (JWT)

## Prototipos y Requisitos
- [Prototipos de Interfaz Gráfica](https://www.figma.com/design/MWKV2WL1MLfTjOanBj6BAx/Prototipos-Ing-Software-II?node-id=0-1&p=f&t=8h5PoVmqxRZCdG2o-0)
- [Levantamiento de Requisitos](https://docs.google.com/spreadsheets/d/1biNSaZJZcjWHdm3X6NpfwFmzO7mdBNpvv1t8wzZ4-a4/edit?gid=762091937#gid=762091937)

## Instalación y Configuración
1. **Clonar el repositorio:**
    ```bash
    git clone https://github.com/usuario/sistema-proyectos-academicos.git
    ```

2. **Requisitos previos:**
    - Java 17+
    - Maven 3.8+
    - Docker y Docker Compose
    - KeyCloak

3. **Construir los microservicios:**
    ```bash
    mvn clean install
    ```

4. **Ejecutar con Docker Compose:**
    ```bash
    docker-compose up --build
    ```
## Créditos
Desarrollado por:
- Jose David Arteaga Ferrnandez
- Juan Camilo Benavides Salazar
- Juan Esteban Chavez Collazos
- Jhoan Sebastian Garcia Camacho
- Juan Diego Perez Martinez

**Profesor:** Libardo Pantoja

---

Este proyecto fue desarrollado como parte de una iniciativa académica para vincular estudiantes y empresas mediante soluciones tecnológicas reales. ¡Gracias por explorar nuestro sistema!
