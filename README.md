# APi Equipos de Futbol

## Introduccion

Esta API esta desarrollada con Java 17 y Spring Boot 3. La misma permite crear, buscar, actualizar y elimnar equipos de futbol. Asi como tambien tiene un sistema de autenticacion reliazdo con JWT que obliga al usuario generar un token y utilizarlo para realizar las acciones ya mencionadas. La misma cuenta con documentacion realizada con Swagger OpenApi 3 para que sea mas amigable visualmente para el usuario.

## Ejecucion

Esta API se puede ejecutar de dos maneras: 
  - De manera local sin utilizar docker. Para lo cual se necesita tener instalado Java 17, maven 3.9.6 asi como tener establecidas correctamente las variables de entorno de java y maven. Una vez cumplido eso, se deben realizar los siguiente pasos: 
    - Correr en el directorio principal el siguiente comando: "mvn clean package install"
    - Luego correr el comando: "mvn spring-boot:run"
  - Utilizando docker. Para estos pasos no se necesita descargar nada ya que todo se descarga dentro del contenedor de docker:
    - Crear la imangen corriendo en el directorio principal el comando: "docker build -t api-teams ."
    - Una vez creada la imagen correr la imagen con el comando: "docker run -p 8080:8080 api-teams"
   
## Uso de la aplicación
Esta aplicación es un simple ABM que tiene solicitudes get, put, post y delete para los equipos. Una vez que la aplicación este corriendo se puede utilizar la misma de dos maneras distintas.

### Postman
Si se usa alguna herramienta como Postman, primero se debe autenticar utilizando el endpoint: /auth/login y en el cuerpo de la solicitud colocar el usuario que por defectoe es {"username": "test", "password": "12345"}. Como respuesta retornara en el caso exitoso un token que debera ser utilizado en todas las soliciudes.
Si se desea crear un usuario se puede hacer con el endpoint /auth/register y en el cuerpo colocar username y password. Para el caso exitoso retornara un token que debera ser usado en todas las solicitudes de la aplicación.

### Swagger
Esta aplicacion cuenta con una documentacion realizada con swagger. Para acceder a la misma una vez que la aplicación este corriendo, se debe ingresar a a la url: /swagger-ui/index.html . En la misma se debera loguear clickeando en el boton "Authorize" con las credenciales correspondientes y a partir de ese momento podra probar los endpoints que quiera. Cabe destacar que en este metodo no esta contemplado crear nuevos usuarios, solo se puede registrar ya existentes.
