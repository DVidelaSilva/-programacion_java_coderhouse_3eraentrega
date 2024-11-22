# Programación Java- Coderhouse
# Tercera Entrega y Final Proyecto - Facturacion 


El proyecto permite gestionar lo relacionado a un comercio y 4 de sus modelos
- Clientes
- Productos
- Facturas
- Detalle de Facturas


#Procedimiento para la APP

- 1.- Se debe levantar una instancia local de BD mysql con un esquema.
- 2.- Se debe crear la base de datos y activarla mediante ejecutando los siguiente script.

```SQL
    CREATE DATABASE IF NOT EXISTS Java_3eraE_coderhouse;
    USE Java_3eraE_coderhouse;
```

- 3.- una una vez creadas la BD, clonar el proyecto e importar al IDE.

        https://github.com/DVidelaSilva/-programacion_java_coderhouse_3eraentrega

- 4.- Configurar el archivo application.properties, principalmente:

```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/Java_3eraE_coderhouse
    spring.datasource.username=<username_de_mysql>
    spring.datasource.password=<password_de_mysql>
```
- 5.- correr la aplicacion springBoot.

- 6.- El paso anterior creara las tablas en BD (vacias) necesarias para el flujo de la aplicacion.

- 7.- importar a postman el collection que se encuentra adjunto en un archivo 
        "59570_Facturacion_3eraEntrega_Diego_Videla.zip" en la plataforma de coderhouse (entrega).

- 8.- una vez importado el collection ya se puede tener acceso a todos los endpoints de la aplicacion los cuales se detallan a continuacion.



# Endpoints Aplicación

## Endpoints Clients

- GET : api/clients -> Este endpoint devuelve el listado completo de clientes que se encuentran registrados en BD.

Respuestas statusCode:
200 - búsqueda exitosa
500 - Problemas internos en el servidor

- GET : api/clients/{id} -> Este endpoint devuele el cliente buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/clients/1 )).

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- POST : api/clients -> Este endpoint permite crear un nuevo cliente en BD. se requiere el firstName, lastName y docNumber (todos String).

Respuestas statusCode:
201 - Creado exitosamente
409 - conflicto - El recurso a crear ya existe en la BD
500 - Problemas internos en el servidor

- PUT: api/clients/{id} -> Este endpoint permite actualizar un cliente buscado segun el ID, todos sus campos son modificables, ( NOTA: id para esta proyecto, un numerico (ej: api/clients/1 )).

Respuestas statusCode:
200 - Actualización exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- DELETE : api/clients/{id} -> Este endpoint elimina de la BD el cliente buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/clients/1 )).

Respuestas statusCode:
204 - No hay contenido que devolver, eliminación exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor



## Endpoints Products

- GET : api/products -> Este endpoint devuelve el listado completo de productos que se encuentran registrados en BD.

Respuestas statusCode:
200 - búsqueda exitosa
500 - Problemas internos en el servidor

- GET : api/products/{id} -> Este endpoint devuele el producto buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/productos/1 )).

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- POST : api/products -> Este endpoint permite crear un nuevo producto en BD. se requiere el description(string), code(string), price(int), stock(int).

Respuestas statusCode:
201 - Creado exitosamente
409 - conflicto - El recurso a crear ya existe en la BD
500 - Problemas internos en el servidor

- PUT: api/products/{id} -> Este endpoint permite actualizar un producto buscado segun el ID, todos sus campos son modificables, ( NOTA: id para esta proyecto, un numerico (ej: api/productos/1 )).

Respuestas statusCode:
200 - Actualización exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- DELETE : api/products/{id} -> Este endpoint elimina de la BD el producto buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/productos/1 ))

Respuestas statusCode:
204 - No hay contenido que devolver, eliminación exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor




## Endpoints Invoice

- GET : api/invoices -> Este endpoint devuelve el listado completo de invoices que se encuentran registrados en BD.

Respuestas statusCode:
200 - búsqueda exitosa
500 - Problemas internos en el servidor

- GET : api/invoices/{id} -> Este endpoint devuele el invoice buscado segun el ID del invoice ( NOTA: id para esta proyecto, un numerico (ej: api/invoices/1 )).

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- GET : api/invoices/client/{id} -> Este endpoint devuele el invoice buscado segun el ID del cliente. Por ende devuelve los invoice creados para el cliente buscado ( NOTA: id para esta proyecto, un numerico (ej: api/invoices/client/1 )).

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- POST : api/invoices/voucher -> Este endpoint permite crear un nuevo invoice en BD. se requiere el clientId que es el ID del cliente al cual se creara el Invoice(voucher), y el productId que es un array de ID´s de productos los cuales se añadiran el invoice(voucher), el response del servicio es un detalle tipo vouicher donde se encuentra el clienID al cual se creo el invoice, una arreglo "lineas" (segun especificacion) el cual contiene la cantidad de productos agregados y el ID de este producto. ademas se devolvera el campo, "total_product" con la cantidad total de productos en el invoice, el campo "total_Sale" con la sumatoria de del valor total de todos los productos en el invoice y la fecha "created_at", esta fecha se obtiene diractamente de la api <<http://worldclockapi.com/api/json/utc/now>> y se especifica con TimeApi. en caso de nbo encontrarse disponible el API externa se creara la fecha en base a la fecha de la maquina local y sera especificada como TimeLocal.

Respuestas statusCode:
201 - Creado exitosamente
500 - Problemas internos en el servidor

- POST : api/invoices/create/{id} -> Este endpoint solo permite crear un invoice vacio a un clienteID, el parametro total debe ir en valor Cero. este se modificara al momento de agregar productos (mediante otro endpoint). (ej: api/invoices/create/1 ).

Respuestas statusCode:
201 - Creado exitosamente
500 - Problemas internos en el servidor

- PUT: api/invoices/{id} -> Este endpoint permite actualizar un invoice buscado segun el ID, solo es posible modificar el campo "total", ( NOTA: id para esta proyecto, un numerico (ej: api/invoices/1 )).

Respuestas statusCode:
200 - Actualización exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- DELETE : api/invoices/{id} -> Este endpoint elimina de la BD el invoice buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/productos/1 ))

Respuestas statusCode:
204 - No hay contenido que devolver, eliminación exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor




## Endpoints Invoice_details

- GET : api/invoice-details -> Este endpoint devuelve el listado completo de invoice-details que se encuentran registrados en BD.

Respuestas statusCode:
200 - búsqueda exitosa
500 - Problemas internos en el servidor

- GET : api/invoice-details/{id} -> Este endpoint devuele el invoice-details buscado segun el ID del invoice-details ( NOTA: id para esta proyecto, un numerico (ej: api/invoice-details/1 )).

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- GET : api/invoice-details/{id}/products -> Este endpoint devuele el detalle de los productos que estan en un invoiceID que es el valor que se pasa como parametro.(ej: api/invoice-details/1/products )

Respuestas statusCode:
200 - búsqueda exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- PUT: api/invoice-details/{id} -> Este endpoint permite actualizar un invoice-details buscado segun el ID, solo es posible modificar el campo "amount", ( NOTA: id para esta proyecto, un numerico (ej: api/invoice-details/1 )).

Respuestas statusCode:
200 - Actualización exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor

- DELETE : api/invoice-details/{id} -> Este endpoint elimina de la BD el invoice-details buscado segun el ID ( NOTA: id para esta proyecto, un numerico (ej: api/invoice-details/1 ))

Respuestas statusCode:
204 - No hay contenido que devolver, eliminación exitosa
404 - Id buscado no existe
500 - Problemas internos en el servidor



## Endpoints TimeResponse

- GET : api/time -> este endpoint devuelve la data obtenida de la API externa 
    http://worldclockapi.com/api/json/utc/now

Respuestas statusCode:
200 - búsqueda exitosa
500 - Problemas internos en el servidor




# Consultas a BD

para poder obtener la data persistida en base de datos los scripts de sql son:

```SQL
    SELECT *
    FROM clients;

    SELECT *
    FROM invoice;

    SELECT *
    FROM products;

    SELECT *
    FROM invoice_details;
```


# DocuApi

Tambien se puede encontrar un detalle completo de los endpoint de la aplicacion levantando el swagger.

http://localhost:8080/swagger-ui/index.html#/