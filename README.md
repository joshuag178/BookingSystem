# Booking System
## Primer Actividad
 * Crear un proyecto base utilizando Spring Initializr con las siguientes características:
    * Maven Project.
    * Language Java.
    * Última versión de Spring Boot predeterminada.
    * Java Versión 8.
    * Agregar en la parte derecha la dependencia de Spring Web.
* Crea un nuevo paquete llamado controller.health y adentro crea la clase HealthController.

## Segunda Actividad
* Definir la interfaz del servicio y crear una implementación utilizando un HashMap para realizar las operaciones CRUD sobre los usuarios:
   * Create.
   * Read.
   * Update.
   * Delete.
* Configurar la implementación del Servicio de Usuarios para que sea inyectable con @Service.
* Implementar el Controlador de Usuarios inyectando el servicio de usuarios y manejando los diferentes métodos requeridos para implementar el CRUD:
  * GET.
  * POST.
  * PUT.
  * DELETE.
* Ejecutar el proyecto y utilizar Postman para verificar el correcto funcionamiento de los respectivos Endpoints.
* Guardar los cambios en el repositorio del proyecto integrador y enviar el enlace con la solución.

## Tercera Actividad
Implementar la capa de persistencia con Spring Data MongoDB.
* Parte 1: Configuración y conexión con el cluster de MongoDB
  * Crear el cluster del proyecto con la configuración requerida para conectar el proyecto de Spring Boot:
    * Crear el cluster.
    * Crear el usuario y contraseña con acceso al cluster.
    * Agregar el acceso desde cualquier lugar al cluster.
  * Agregar la dependencia de spring-boot-starter-data-mongodb a tu pom.xml:
    ```xml
    <dependency> 
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
    <groupId>org.springframework.boot</groupId> 
    </dependency>
  * Agregar la variable de entorno al proyecto para la configuración de la URI de conexión de MongoDB.
  * Ejecutar la aplicación y verificar la conexión con el cluster de MongoDB.
* Parte 2: Implementando tus documentos y repositorios para almacenar tus datos en MongoDB
  * Implementar la clase Document del modelo que se quiere almacenar en la base de datos con sus respectivos atributos. 
  * Crear la respectiva Interfaz del Repositorio para el documento creado en 1.
  * Implementar la interfaz y el servicio para realizar las operaciones CRUD sobre el modelo almacenado en la base de datos.
  * Agregar la anotación @Service a el controlador respectivo e implementa la lógica para utilizar el servicio que te permite almacenar y consultar los datos desde el cluster de MongoDB.
  * Ejecutar el proyecto y utilizar Postman para verificar el correcto funcionamiento de los respectivos Endpoints y verificar que la información sea almacenada en MongoDB Atlas.
  * Guardar los cambios en el repositorio del proyecto integrador y envíar el enlace con la solución.

