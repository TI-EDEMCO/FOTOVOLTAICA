# Documentación de Microservicios en Java

## Versión Java

Todo el proyecto está en la versión **17** de **Java**

## Introducción

Esta documentación cubre la arquitectura de microservicios desarrollada en Java. Cada microservicio es una aplicación independiente que se ejecuta en su propio proceso y se comunica con otros microservicios a través de HTTP o mensajes. Esta arquitectura permite la escalabilidad, el aislamiento de fallos y la implementación continua de aplicaciones. A continuación, se detallan los diferentes microservicios, incluyendo sus puertos, endpoints, dependencias y ejemplos de código.

## Microservicio: Integracion Siesa

### Descripción

Este microservicio cumple la funcion de comunicarse con la API de Siesa para realizar las consultas de los datos necesarios para enviar de vuelta a siesa para la debida facturacion por planta, este microservicio tambien se encarga de listar y editar plantas.

### Puerto

- 9090

### Metodos

-     public ResponseEntity<String> getIntDatosFacturaClienteCen()
    Este metodo es el encargado de traer los datos de factura de todas las plantas desde Siesa.
-     public ResponseEntity<String> getIntDatosFacturaDocumentoCen(String nitCliente)
    Este metodo es el encargado de traer datos de los documento por cliente utilizando el nit del cliente.
-     public ResponseEntity<String> getIntDatosFacturaMvtoCen()
    Este metodo es el encargado de traer los datos de movimiento de la factura.
-     public ResponseEntity<String> postConectoresImportar(VentasServicios ventasServicios)
    Este metodo es el encargado de hacer el post a Siesa con el JSON ya completo.
-     public ResponseEntity<List<String>> getNitsByCentroOperacion(List<IniciarFacturacionDto> centroOperacionList)
    Metodo encargado de traer los nits de cada planta.
-     public ResponseEntity<List<VentasServicios>> llenarDatos2 (List<IniciarFacturacionDto> centroOperacionList, LocalDate date)
    Metodo encargado de llenar el JSON a enviar a Siesa, utilizando una lista que contiene el nombre de la planta y el id de esta, y la fecha del mismo dia que se genera la facturacion, este metodo utiliza todos los metodos del servicio para crear el JSON en su totalidad.
-     public Double findGeneracionActualByIdPlantaAndDate(String idPlanta, Integer anio, Integer mes)
    Metodo encargado de enviar la lista de JSON completos a Siesa.
### Endpoints

-     @PostMapping("/enviar_factura_siesa")    
      public ResponseEntity<?> enviarFacturaSiesa(@RequestBody List<IniciarFacturacionDto> iniciarFacturacionDtoList, @RequestParam("date") LocalDate date)
    Este EndPoint POST es el encargado de enviar la lista de JSON completos a Siesa.
-     @GetMapping("/all")
      public ResponseEntity<?> findAllPlantas()
    Este EndPoint GET es el encargado de listar todas las plantas
-     @GetMapping("/idplanta")
      public String findIdPlantaByNombrePlanta(@RequestParam("nombrePlanta") String nombrePlanta)
    Este EndPoint GET es en el encargado de traer el idPlanta por el nombrePlanta, EndPoint utilizado por el microservicio generacion por feignclient.
-     @GetMapping("/idoperador")     
      public Long findIdOperadorByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta)
    Este EndPoint GET es el encargado de traer el idOperador por el idPlanta, utilizado por el microservicio generacion por feign client.
-     @GetMapping("/valorUnidad")
      public Double findValorUnidadByIdPlanta(@RequestParam(name = "idPlanta") String idPlanta)
    Este EndPoint GET es el encargado de traer el valor unidad por idPlanta, utilizado por el microservicio generacion por feign client.
-     @PatchMapping("/updatePlanta")
      public List<PlantaDto> updatePlanta(@RequestBody List<PlantaDto> plantaDtoList)
    Este EndPoint PATCH es el encargado de actualizar la planta con porcentajeAumento o asunto o urlImg.
-     @GetMapping("/list_especiales")
      private ResponseEntity<?> listClientesEspeciales()
    Este EndPoint GET es el encargado de listar todos los clientes con facturacion especial.
### Dependencias y Frameworks

- SpringBoot
- Spring Web
- Spring Data Jpa
- PostgreSQL Driver
- Eureka Discovery Client
- Config Client
- Lombok
- OpenFeign

### Ejemplo de Código

```java
@PostMapping("/enviar_factura_siesa")
public ResponseEntity<?> enviarFacturaSiesa(@RequestBody List<IniciarFacturacionDto> iniciarFacturacionDtoList, @RequestParam("date") LocalDate date){
    return service.envioFacturas(iniciarFacturacionDtoList,date);
}
```

## Microservicio: Configuración

### Descripción

Este microservicio es el encargado de guardar las configuraciones de todos los otros microservicios en la carpeta configurations en el paquete resources, todas las configuraciones estan en archivos .yml.

### Puerto

- 8888

### Endpoints

Este metodo no cuenta con EndPoints.

### Dependencias y Frameworks

- Spring Boot
- Config Server

### Ejemplo de Código

```java
@EnableConfigServer
@SpringBootApplication
public class MicroserviceConfigApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceConfigApplication.class, args);
  }

}
```

## Microservicio: Eureka

### Descripción

Funciona como un registro de servicios donde otros microservicios pueden registrarse y descubrir otros servicios para interactuar.

### Puerto

- 8761

### Endpoints

Este microservicio no cuenta con EndPoints

### Dependencias y Frameworks

- SpringBoot
- Eureka Server
- Spring Boot Actuator

### Ejemplo de Código

```java
@EnableEurekaServer
@SpringBootApplication
public class MicroserviceEurekaApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceEurekaApplication.class, args);
  }

}
```

## Microservicio: Factura

### Descripción

Microservicio encargado de listar las facturas de cada una de las plantas según los requerimientos del cliente.

### Puerto

- 8060

### Endpoints

-     @GetMapping("/filter")
      public ResponseEntity<?> findFacturaByNitAndDate(@RequestParam("idPlanta") String idPlanta, @RequestParam(value = "date", required = false) String date) throws FacturaNotFoundException
    EndPoint GET encargado de traer la factura de una planta en un mes especifico o traer todas las facturas de una planta guardadas en la base de datos.
-     @GetMapping("/planta")
      public ResponseEntity<?> listFacturaByPlanta(@RequestParam("idPlanta") String idPlanta) throws FacturaNotFoundException
    EndPoint GET encargado de traer las facturas de una planta por idPlanta.
-     @GetMapping("/date")
      public ResponseEntity<?> listFacturaByDate(@RequestParam("date") String date) throws FacturaNotFoundException
    EndPoint GET encargado de traer todas las facturas de todas las plantas por la fecha.
### Dependencias y Frameworks

- SpringBoot
- Spring Web
- Spring Data Jpa
- PostgreSQL Driver
- Eureka Discovery Client
- Config Client
- Lombok

### Ejemplo de Código

```java
@GetMapping("/filter")
public ResponseEntity<?> findFacturaByNitAndDate(@RequestParam("idPlanta") String idPlanta, @RequestParam(value = "date", required = false) String date) throws FacturaNotFoundException{
    return ResponseEntity.ok(facturaService.findByIdPlantaAndDate(idPlanta, date));
}
```

## Microservicio: Facturacion especial

### Descripción

El propósito del microservicio de facturación especial, es almacenar dos variables para realizar un cálculo, estos valores los toma para todas las plantas especiales

### Puerto

- 9081

### Endpoints

- url base: **/api/facturacion_especial**
- listar todos los cálculos de la tabla facturación especial: **/all**
- crear una nueva facturación especial: **/create**

### Dependencias y Frameworks

- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-cloud-starter-config
- spring-cloud-starter-netflix-eureka-client
- postgresql

### Ejemplo de Código

```java
@PostMapping("/create")
@ResponseStatus(HttpStatus.CREATED)
public void createFacturacionEspecial(@RequestBody FacturacionEspecialDTO facturacionEspecial) {
    iFacturacionEspecialService.save(facturacionEspecial);
}
```

## Microservicio: Gateway

### Descripción

Este microservicio es el encargado de gestionar las solicitudes realizadas a la API y redigirlas a los microservicios correspondientes en el proyecto.

### Puerto

- 8080

### Endpoints

Este microservicio no cuenta con EndPoints

### Dependencias y Frameworks

- Spring Boot
- Config Cliente
- Eureka Discovery Client
- Gateway
- JJWT :: Impl
- JJWT :: Extensions :: Jackson

### Ejemplo de Código

```java
// Código de ejemplo para microservice-gateway
```

## Microservicio: Generacion

### Descripción

<!-- Descripción del microservicio-generation -->

Microservicio encargado de la gestión de la generación de energía. Permite calcular y almacenar datos de generación de energía, ahorro acumulado y otros indicadores relevantes a lo largo del tiempo.

### Puerto

- 9092

### Endpoints
```java
// @PostMapping("/calculos")
public ResponseEntity<?> calculosGeneracion(@RequestBody List<DatosGeneracionDTO> datosGeneracionDTOList){
}
```
Este endpoint es el encargado de hacer cálculos de generación, ahorro y  acumulados 

```java
// @GetMapping("/valor_unidad")
public ValorUnidadDTO findLastValorUnidad(){
}
```
Este endpoint se encarga de encontrar el último valor unidad por planta
```java
// @GetMapping("/generacion_actual")
public Double findGeneracionActualByIdPlantaAndDate(@RequestParam(name = "idPlanta")String idPlanta,
                                                    @RequestParam(name = "anio")Integer anio,
                                                    @RequestParam(name = "mes")Integer mes){
}
```
Este endpoint es utilizado por el microservicio de Integración Siesa a través de FeignClient y se encarga de traer la generación actual por el idPlanta y la fecha

```java
@GetMapping("/valor_total")
public Double findValorTotalByIdPlantaAndDate(@RequestParam(name = "idPlanta")String idPlanta,
                                              @RequestParam(name = "anio")Integer anio,
                                              @RequestParam(name = "mes")Integer mes){
}
```
Este endpoint es utilizado por el microservicio de Integración Siesa a través de FeignClient y se encarga de encontrar el valor total por idPlanta y fecha
### Dependencias y Frameworks

- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Eureka Discovery Client
- Config Client
- Lombok
- Spring Boot Actuator
- OpenFeign

### Ejemplo de Código

```java
 public ResponseEntity<?> calculate(List<DatosGeneracionDTO> datosGeneracionDTOList) {
  int mesActual = datosGeneracionDTOList.get(0).getFechaFactura().getMonthValue();
  Integer anio = datosGeneracionDTOList.get(0).getFechaFactura().getYear();

  if (!findGenerationsByDate(anio, mesActual).isEmpty()) {
    return ResponseEntity.ok("Ya hay una generación para este mes");
  }

  for (int i = 0; i <= datosGeneracionDTOList.size()-1; i++){

    if (!datosGeneracionDTOList.get(i).getPlantName().equals("Sede Edemco")){
     //Se traen los campos necesarios para llenar el objeto de generación
      
      Double generacionAcumulada = generatorRepository.findGeneracionAcumuladaByDateAndPlanta(anio, mesActual-1, idPlanta);
      if (generacionAcumulada != null){
        generacionAcumulada = generacionAcumulada + generacionActual;
      }else {
        generacionAcumulada = generacionActual;
      }
      Double valorTotal = generacionActual * valorUnidad;
      Double ahorroActual = diferencia * generacionActual;

      Double ahorroAcumulado = generatorRepository.findAhorroAcumuladoByDateAndPlanta(anio, mesActual-1, idPlanta);
      if (ahorroAcumulado != null ){
        ahorroAcumulado = ahorroAcumulado + ahorroActual;
      }else {
        ahorroAcumulado = ahorroActual;
      }
      Double ahorroCodosActual = generacionActual * 0.504;
      Double ahorroCodosAcumulado = generatorRepository.findAhorroCodosAcumuladoByDateAndPlanta(anio, mesActual-1, idPlanta);
      if (ahorroCodosAcumulado != null){
        ahorroCodosAcumulado = ahorroCodosActual + ahorroCodosAcumulado;
      }else{
        ahorroCodosAcumulado = ahorroCodosActual;
      }

      Generator generator = new Generator();
      //Se llenan los campos de generación
    }
  }
}
```

## Microservicio: Remitentes

### Descripción

Microservicio encargado de la gestion de remitentes para cada una de las plantas, en este microservicio esta implementado todo el CRUD para remitentes y las consultas necesarias para el usuario.

### Puerto

- 8070

### Endpoints

-     @PostMapping("/create")
      public ResponseEntity<?> createEmail(@RequestBody Email email) throws EmailConflictException
    Este EndPoint POST es el encargado de crear Emails.
-     @DeleteMapping("/delete")
      public ResponseEntity<?> deleteEmail(@RequestParam("idEmail")Long idEmail) throws EmailNotFoundException
    Este EndPoint DELETE es el encargado de eliminar Emails.
-     @GetMapping("/find")
      public ResponseEntity<?> findAll(@RequestParam("idPlanta") String idPlanta) throws EmailNotFoundException {
    Este EndPoint GET es el encargado de encontrar emails por el idPlanta.
### Dependencias y Frameworks

- SpringBoot
- Spring Web
- Spring Data Jpa
- PostgreSQL Driver
- Eureka Discovery Client
- Config Client
- Lombok
- Spring Boot Actuator

### Ejemplo de Código

```java
    @GetMapping("/find")
    public ResponseEntity<?> findAll(@RequestParam("idPlanta") String idPlanta) throws EmailNotFoundException {
        return ResponseEntity.ok(emailService.findByIdPlanta(idPlanta));
    }
```

## Microservicio: Seguridad

### Descripción

Microservicio encargado de la seguridad de la aplicacion, este microservicio implementa el JSONWebToken para la seguridad.

### Puerto

- 8050

### Endpoints

-     @PostMapping("/register")
      public String addNewUser(@RequestBody UserCredential userCredential)
    EndPoint POST encargado de registrar un nuevo usuario.
-     @PostMapping("/token")
      public String getToken(@RequestBody AuthRequest authRequest)
    EndPoint POST encargado de obtener el Token.
-     @GetMapping("/validate")
      public String validateToken(@RequestParam("token") String token)
    EndPoint GET encargado de validar el Token.
-     @GetMapping("/login")
      public String Login()
    EndPoint GET encargado del login del usuario.
### Dependencias y Frameworks

- Spring Boot
- Spring Data JPA
- Spring Web
- Config Client
- Eureka Discovery Client
- Spring Security
- PostgreSQL Driver
- JJWT :: Impl
- JJWT :: Extensions :: Jackson
- JJWT :: API
- Lombok

### Ejemplo de Código

```java
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()){
            return authService.generateToken(authRequest.getUsername());
        }else {
            throw new RuntimeException("Invalid Access");
        }
    }
```

## Microservicio: Operadores

### Descripción

Microservicio encargado de la gestion de operadores y tarifa operadores.

### Puerto

- 9091

### Endpoints

-     @GetMapping
      public ResponseEntity<List<OperadorDto>> listarOperadores()
    EndPoint GET encargado de listar todos los operadores.
-     @PostMapping
      public List<TarifaOperadorDto> registrarTarifa(@RequestBody List<TarifaOperadorDto> tarifaOperadorList)
    EndPoint POST encargado de registrar una nueva tarifa operador.
-     @GetMapping("/last_tarifas")
      public ResponseEntity<?> findLastTarifaOperadores()
    EndPoint GET encargado de listar las ultimas tarifas ingresadas.
-     @GetMapping("/tarifaoperadordto")
      public TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(@RequestParam(name = "idOperador")Long idOperador,
      @RequestParam(name = "mes") Integer mes)
    EndPoint GET encargado de encontrar la tarifa operador según el idOperador y el mes de la tarifa.

### Dependencias y Frameworks

- SpringBoot
- Spring Web
- Spring Data Jpa
- PostgreSQL Driver
- Eureka Discovery Client
- Config Client
- Lombok
- Spring Boot Actuator

### Ejemplo de Código

```java
@GetMapping("/tarifaoperadordto")
public TarifaOperadorDto findTarifaOperadorByIdOperadorAndMonth(@RequestParam(name = "idOperador")Long idOperador,
                                                                @RequestParam(name = "mes") Integer mes){
  return tarifaOperadorService.findTarifaOperadorByIdOperadorAndMonth(idOperador, mes);
}
```
