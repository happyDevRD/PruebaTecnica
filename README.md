Para configurar la base de datos MySQL para ser utilizada en la aplicación Spring Boot, necesitas asegurarnos de que la base de datos y el usuario estén correctamente configurados en MySQL

### Paso 1: Crear la Base de Datos en MySQL

1. **Iniciar sesión en MySQL:**
   ```bash
   mysql -u root -p
   ```

2. **Crear la Base de Datos:**
   Ejecuta el siguiente comando en MySQL para crear la base de datos:
   ```sql
   CREATE DATABASE client_person_management_db;
   ```

### Paso 2: Configurar el Usuario en MySQL

Ahora toca crear el usuario `cmp_user`, sigue estos pasos.
1. **Crear Usuario:**
   ```sql
   CREATE USER 'cmp_user'@'localhost' IDENTIFIED BY '@8493547557Az@#$';
   ```

2. **Otorgar Permisos:**
   ```sql
   GRANT ALL PRIVILEGES ON client_person_management_db.* TO 'cmp_user'@'localhost';
   ```

3. **Aplicar los Cambios:**
   ```sql
   FLUSH PRIVILEGES;
   ```

### Paso 3: Configurar el Archivo `application.properties` de Spring Boot

En el proyecto de Spring Boot, abre o crea un archivo llamado `application.properties` en el directorio `src/main/resources/` de los dos Microservicios y agrega o verifica las siguientes líneas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/client_person_management_db
spring.datasource.username=cmp_user
spring.datasource.password=@8493547557Az@#$
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8082
```

Esto configurará de aplicación Spring Boot para que se conecte a la base de datos MySQL que has configurado. Asegúrate de que el proyecto de Spring Boot tenga las dependencias necesarias para MySQL y JPA/Hibernate. 

``` sql
USE client_person_management_db;
```

```
## Script de Creación de Base de Datos (BaseDatos.sql)
-- Creación de la tabla 'personas'
CREATE TABLE `personas` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(100) NOT NULL,
    `genero` VARCHAR(255) NOT NULL,
    `edad` INT NOT NULL,
    `identificacion` VARCHAR(50) NOT NULL,
    `direccion` VARCHAR(255) NOT NULL,
    `telefono` VARCHAR(15) NOT NULL,
    `dtype` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Creación de la tabla 'clientes', que hereda de 'personas'
CREATE TABLE `clientes` (
    `id` BIGINT NOT NULL,
    `contraseña` VARCHAR(255) NOT NULL,
    `estado` BOOLEAN NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `personas`(`id`)
);

-- Creación de la tabla 'cuentas'
CREATE TABLE `cuentas` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `numero_cuenta` VARCHAR(255) NOT NULL UNIQUE,
    `tipo` VARCHAR(255) NOT NULL,
    `saldo_inicial` DECIMAL(10,2) NOT NULL,
    `estado` VARCHAR(255) NOT NULL,
    `cliente_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cliente_id`) REFERENCES `clientes`(`id`)
);

-- Creación de la tabla 'movimientos'
CREATE TABLE `movimientos` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `fecha` TIMESTAMP NOT NULL,
    `tipo_movimiento` VARCHAR(255) NOT NULL,
    `valor` DECIMAL(10,2) NOT NULL,
    `cuenta_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas`(`id`)
);

### Consultas SQL para Insertar Datos de Prueba
```
#### Inserción de Personas

``` 
INSERT INTO personas (nombre, genero, edad, identificacion, direccion, telefono, dtype) VALUES 
('Jose Lema', 'Masculino', 35, '111111', 'Otavalo sn y Principal', '098254785', 'Cliente'),
('Marianela Montalvo', 'Femenino', 30, '222222', 'Amazonas u NNUU', '097548965', 'Cliente'),
('Juan Osorio', 'Masculino', 40, '333333', '13 Junio y Equinoccional', '098874587', 'Cliente');
```

#### Inserción de Clientes

```
INSERT INTO clientes (id, contraseña, estado) VALUES (1, '1234', true), (2, '5678', true), (3, '1245', true);
```
#### Inserción de Cuentas
```
INSERT INTO cuentas (numero_cuenta, tipo, saldo_inicial, estado, cliente_id) VALUES 
('478758', 'Ahorros', 2000.00, 'ACTIVA', (SELECT id FROM personas WHERE nombre = 'Jose Lema')),
('225487', 'Corriente', 100.00, 'ACTIVA', (SELECT id FROM personas WHERE nombre = 'Marianela Montalvo')),
('495878', 'Ahorros', 0.00, 'ACTIVA', (SELECT id FROM personas WHERE nombre = 'Juan Osorio')),
('496825', 'Ahorros', 540.00, 'ACTIVA', (SELECT id FROM personas WHERE nombre = 'Marianela Montalvo')),
('585545', 'Corriente', 1000.00, 'ACTIVA', (SELECT id FROM personas WHERE nombre = 'Jose Lema'));
```
#### Inserción de Movimientos
```
INSERT INTO movimientos (fecha, tipo_movimiento, valor, cuenta_id) VALUES ('2023-12-22 00:00:00', 'RETIRO', -575.00, (SELECT id FROM cuentas WHERE numero_cuenta = '478758')), ('2023-12-22 00:00:00', 'DEPOSITO', 600.00, (SELECT id FROM cuentas WHERE numero_cuenta = '225487')), ('2023-12-22 00:00:00', 'DEPOSITO', 150.00, (SELECT id FROM cuentas WHERE numero_cuenta = '495878')), ('2023-12-22 00:00:00', 'RETIRO', -540.00, (SELECT id FROM cuentas WHERE numero_cuenta = '496825'));
```

Esta guía describirá cómo interactuar con cada endpoint, incluyendo los métodos HTTP, las rutas y los parámetros requeridos.

### Microservicio: Gestión de Cuentas

#### 1. Crear Cuenta

- **Método:** POST
- **Endpoint:** `/cuentas`
- **Cuerpo de la Solicitud:** JSON representando una cuenta.
- **Descripción:** Crea una nueva cuenta.

#### 2. Obtener Todas las Cuentas

- **Método:** GET
- **Endpoint:** `/cuentas`
- **Descripción:** Devuelve una lista de todas las cuentas.

#### 3. Obtener Cuenta por ID

- **Método:** GET
- **Endpoint:** `/cuentas/{id}`
- **Parámetros de Ruta:**
    - `id`: ID de la cuenta.
- **Descripción:** Devuelve una cuenta específica por su ID.

#### 4. Actualizar Cuenta

- **Método:** PUT
- **Endpoint:** `/cuentas/{id}`
- **Parámetros de Ruta:**
    - `id`: ID de la cuenta.
- **Cuerpo de la Solicitud:** JSON con la información actualizada de la cuenta.
- **Descripción:** Actualiza una cuenta existente.

#### 5. Eliminar Cuenta

- **Método:** DELETE
- **Endpoint:** `/cuentas/{id}`
- **Parámetros de Ruta:**
    - `id`: ID de la cuenta.
- **Descripción:** Elimina una cuenta existente.

#### 6. Crear Cuenta para Cliente

- **Método:** POST
- **Endpoint:** `/cuentas/crearParaCliente/{clienteId}`
- **Parámetros de Ruta:**
    - `clienteId`: ID del cliente.
- **Cuerpo de la Solicitud:** JSON representando una cuenta.
- **Descripción:** Crea una nueva cuenta asociada a un cliente específico.

### Microservicio: Gestión de Movimientos

#### 7. Registrar Movimiento

- **Método:** POST
- **Endpoint:** `/movimientos`
- **Cuerpo de la Solicitud:** JSON representando un movimiento.
- **Descripción:** Registra un nuevo movimiento.

#### 8. Obtener Movimientos por Cuenta

- **Método:** GET
- **Endpoint:** `/movimientos/cuenta/{cuentaId}`
- **Parámetros de Ruta:**
    - `cuentaId`: ID de la cuenta.
- **Descripción:** Devuelve los movimientos para una cuenta específica.

#### 9. Obtener Movimientos por Rango de Fechas

- **Método:** GET
- **Endpoint:** `/movimientos`
- **Parámetros de Consulta:**
    - `fechaInicio`: Fecha de inicio en formato `yyyy-MM-dd`.
    - `fechaFin`: Fecha de fin en formato `yyyy-MM-dd`.
- **Descripción:** Devuelve los movimientos en un rango de fechas especificado.

### Microservicio: Reportes

#### 10. Obtener Reporte de Estado de Cuenta

- **Método:** GET
- **Endpoint:** `/reportes`
- **Parámetros de Consulta:**
    - `fechaInicio`: Fecha de inicio en formato `yyyy-MM-dd`.
    - `fechaFin`: Fecha de fin en formato `yyyy-MM-dd`.
    - `clienteId`: ID del cliente.
- **Descripción:** Devuelve un reporte de estado de cuenta para un cliente específico en un rango de fechas.
