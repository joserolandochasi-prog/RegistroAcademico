# Proyecto Final - Registro Académico y Asistencia

## Descripción del proyecto
Tema: Persistencia de datos local para la gestión de control académico. 
Título del Proyecto: Sistema Móvil de Gestión de Asistencia y Control Académico (RegistroAcademico)

El sistema permite manejar distintos roles dentro de la aplicación:
- **Administrador:** crea usuarios y asigna roles.
- **Docente:** crea materias/paralelos y registra la asistencia de los estudiantes inscritos.
- **Estudiante:** se inscribe en materias y consulta su información académica/asistencia.

La aplicación fue desarrollada aplicando arquitectura **MVVM**, persistencia local con **Room Database** y consumo de servicios externos mediante **Retrofit**.

---

## Tecnologías utilizadas
- Kotlin
- Android Studio
- Room Database
- Retrofit
- RecyclerView
- ViewBinding
- Material Design 3
- GitHub

---

## Funcionalidades principales
- Inicio de sesión por roles
- Gestión de usuarios
- Asignación de roles
- Creación de materias y paralelos
- Inscripción de estudiantes
- Registro de asistencia
- Visualización de asistencia
- Estadísticas básicas
- Persistencia local offline con Room
- Integración con API mediante Retrofit

---

## Arquitectura
El proyecto utiliza la arquitectura **MVVM (Model - View - ViewModel)** para separar la lógica de negocio, la persistencia de datos y la interfaz gráfica.

---

## Diagrama Entidad Relación (DER)

![DER](screenshots/der.jpeg)

---

## Capturas de pantalla

### Inicio de sesión
![Login](screenshots/Login.png)

### Panel de administrador
![Admin](screenshots/PanelAdmin.png)

### Panel de docente
![Docente](screenshots/PanelDocente.png)

### Registro de asistencia
![Asistencia](screenshots/TomaAsistencia.png)

### Estadísticas / resumen
![Estadisticas](screenshots/PanelEsAsis.png)

---

## Integrantes
- Chasi José
- Chiriboga Maria José

---

## Repositorio
Proyecto desarrollado como parte del examen final de la materia de Aplicaciones Móviles.
