# Diseño y Creación de un Backend con Capa de Seguridad y Pruebas Unitarias
Selecciona alguno de los siguientes proyectos e implmente las tareas a realizar para demostrar tus conocmientos de las diferentes tecnologías que debe dominar un desarrollador Java Backend Nivel Intermedio:

Opción 1: Sistema de Gestión de Biblioteca
Contexto: Desarrolla un backend para un sistema de gestión de biblioteca que permita a los usuarios buscar, reservar y prestar libros. El sistema debe manejar información de libros, usuarios y préstamos.

Opción 2: Plataforma de Reservas de Restaurantes
Contexto: Implementa un backend para una plataforma de reserva de mesas en restaurantes. El sistema debe permitir a los usuarios ver la disponibilidad, reservar mesas y cancelar reservas. Además, los restaurantes deben poder gestionar sus mesas y horarios.

Opción 3: Aplicación de Seguimiento de Gastos
Contexto: Crea un backend para una aplicación de seguimiento de gastos personales. Los usuarios deben poder registrar sus ingresos y gastos, categorizarlos y ver resúmenes y tendencias de sus finanzas.

Opción 4: Sistema de Administración de Cursos en Línea
Contexto: Desarrolla un backend para un sistema de gestión de cursos en línea. Esto incluye la inscripción de estudiantes, la gestión de cursos y módulos, y la asignación y seguimiento de tareas y exámenes.

Requisitos Técnicos:
* Lenguaje de programación: Java
* Framework: Spring Boot
* Bases de datos: MongoDB y una base de datos relacional (MySQL o PostgreSQL)
* Seguridad: JWT (JSON Web Tokens)
* Principio de Inversión de Control

Tareas a Realizar:
1. Configuración Inicial:
   * Crea un proyecto Spring Boot.
   * Configura las dependencias para MongoDB y una base de datos relacional.
   

2. Modelo de Datos:
   * Define clases del modelo de datos para tu caso específico, como entidades y documentos.


3. Conexión con Bases de Datos:
   * Implementa servicios para persistencia con MongoDB y la base de datos relacional. 
   * Implementa los repositorios y las clases necesarias para cada tipo de base de datos. 


4. Controlador REST:
   * Crea un controlador REST con endpoints para operaciones CRUD y otras funcionalidades específicas de tu caso.
   

5. Servicio de Negocio:
   * Implementa la lógica de negocio.


6. Principio de Inversión de Control:
   * Utiliza la inversión de control para cambiar entre bases de datos.
   

7. Seguridad:
   * Implementa seguridad utilizando JWT. 
   * Configura la autenticación y autorización en tus endpoints.


8. Pruebas Unitarias:
   * Escribe pruebas unitarias para los componentes clave de tu aplicación (modelos, repositorios, servicios, controladores). 
   * Utiliza frameworks de pruebas como JUnit y Mockito.

Criterios de Aceptación:
* Cumplimiento del modelo de madurez Richardson nivel 2. 
* Uso correcto de verbos HTTP y códigos de estado. 
* Implementación adecuada de Spring Data. 
* Funcionalidad CRUD completa. 
* Manejo de excepciones. 
* Seguridad con JWT implementada correctamente. 
* Cobertura de pruebas unitarias en componentes críticos.

Instrucciones:
* Diseña y crea un proyecto de Spring Boot que se ajuste al contexto y requisitos dados. 
* Sigue las tareas detalladas para desarrollar la solución. 
* Asegúrate de que el proyecto funcione correctamente y cumpla con los criterios de aceptación. 
* Documenta tu código y comparte tu solución, enfocándote en las clases y componentes principales.

Atributos de Calidad Esperados:
* Uso de mejores prácticas de codificación. 
* Adherencia a los principios SOLID. 
* Claro uso de la inyección de dependencias. 
* Uso correcto de las anotaciones de Spring Boot. 
* Conexión efectiva con capas de persistencia. 

Comparte el enlace a tu repositorio con la implementación.
