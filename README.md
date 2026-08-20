# Sistema de Inventario y Almacenes

Aplicación web empresarial para la gestión de inventario, almacenes, compras, ventas y control de kardex, construida sobre **Jakarta EE 10** con JSF/PrimeFaces y desplegada en Open Liberty.

Desarrollada como proyecto de la asignatura PRN335 (Programación V) — Universidad de El Salvador, Facultad Multidisciplinaria de Occidente.

---

## Stack

| Capa | Tecnología |
|---|---|
| Plataforma | Jakarta EE 10 (Jakarta EE API 10.0.0) |
| Vista | Jakarta Faces (JSF) + Facelets, PrimeFaces 15, PrimeFlex 3 |
| Persistencia | JPA 3.1 / EclipseLink 4.0.2 |
| Base de datos | PostgreSQL 16 (driver 42.7.3) |
| Servidor | Open Liberty (perfil Jakarta EE 10) |
| Build | Maven, Java 17+ |
| Pruebas | JUnit 5, Mockito 5 |

---

## Decisiones de arquitectura

El proyecto está organizado siguiendo el patrón **BCE (Boundary–Control–Entity)**:

```
core/
├── entity/    18 entidades JPA (Almacen, Producto, Kardex, Compra, Venta…)
├── control/   Capa de acceso a datos (DAOs)
└── boundary/  Beans de presentación JSF
```

### Capa de datos genérica

En lugar de escribir un DAO por entidad, la lógica común vive en una clase base parametrizada:

```java
public abstract class InventarioDefaultDataAccess<T>
        implements InventarioDAOInterface<T>, Serializable {

    protected final Class<T> entityClass;

    public abstract EntityManager getEntityManager();
    // create / update / delete / findById / findRange / count
}
```

Cada DAO concreto solo declara su tipo y expone su `EntityManager`. Los métodos `findRange(first, max)` y `count()` se construyen con la **Criteria API** de forma type-safe, sin JPQL en cadenas de texto.

### Bean CRUD reutilizable

`DefaultFrm<T, K extends Serializable>` implementa el ciclo CRUD completo de la vista una sola vez: estado del formulario (`ESTADO_CRUD`), manejo de mensajes de Faces y un `LazyDataModel` de PrimeFaces conectado directamente a la paginación del DAO.

```java
model.load(first, pageSize, sortBy, filterBy)
    → dao.findRange(first, pageSize)   // solo la página solicitada llega a memoria
    → dao.count()                       // total real para el paginador
```

Las nueve pantallas del sistema (Cliente, Producto, Proveedor, Tipo de Almacén, Tipo de Producto, Unidad de Medida…) heredan de esta base y aportan únicamente su DAO y la conversión de su clave primaria.

### Modelo de datos

18 entidades con relaciones bidireccionales y claves compuestas donde el dominio lo requiere:

- **Catálogo:** `Producto`, `TipoProducto`, `Caracteristica`, `UnidadMedida`, `TipoUnidadMedida`
- **Almacenamiento:** `Almacen`, `TipoAlmacen`
- **Movimientos:** `Compra` / `CompraDetalle`, `Venta` / `VentaDetalle`
- **Trazabilidad:** `Kardex` / `KardexDetalle`
- **Terceros:** `Cliente`, `Proveedor`

La asociación producto–tipo se modela con entidad intermedia (`ProductoTipoProducto`) para permitir características propias de cada combinación.

### Internacionalización

Interfaz disponible en cuatro idiomas mediante `ResourceBundle`: español (por defecto), inglés, francés y alemán — 115 claves por archivo.

### Manejo de fechas

`OffsetDateTimeConverter` resuelve la conversión entre los componentes de PrimeFaces y `OffsetDateTime`, evitando la pérdida de zona horaria en los registros de kardex.

---

## Pruebas

Pruebas unitarias con JUnit 5 y Mockito sobre la capa de acceso a datos, con el `EntityManager` simulado para aislar la lógica del DAO de la base de datos:

- `ClienteDAOTest`
- `ProductoDAOTest`
- `ProductoTipoProductoDAOTest`
- `TipoAlmacenDAOTest`

```bash
./mvnw test
```

---

## Ejecución local

**Requisitos:** JDK 17+, Maven 3.9+, PostgreSQL 16, Open Liberty con el perfil Jakarta EE 10.

1. **Base de datos**

   ```bash
   createdb inventario
   ```

   El esquema se genera desde las entidades JPA o se importa desde el respaldo del proyecto.

2. **DataSource en Liberty** — en `server.xml`:

   ```xml
   <library id="postgres-lib">
       <fileset dir="${shared.resource.dir}" includes="postgresql-42.7.3.jar"/>
   </library>

   <dataSource id="InventarioDS" jndiName="jdbc/InventarioDS">
       <jdbcDriver libraryRef="postgres-lib"/>
       <properties.postgresql serverName="localhost" portNumber="5432"
                              databaseName="inventario"
                              user="${env.DB_USER}" password="${env.DB_PASSWORD}"/>
   </dataSource>
   ```

   La unidad de persistencia (`inventarioPU`) es JTA y toma la conexión de este JNDI; no hay credenciales en el código.

3. **Compilar y desplegar**

   ```bash
   ./mvnw clean package
   ./mvnw liberty:dev
   ```

   Disponible en `http://localhost:9080/InventarioWebApp/paginas/Inicio.xhtml`

---

## Estado

Proyecto académico funcional. Pendiente en la hoja de ruta:

- Filtrado y ordenamiento server-side en `LazyDataModel` (actualmente solo paginación)
- Pantallas de Compra, Venta y Kardex sobre la misma base `DefaultFrm`
- Autenticación y control de roles con Jakarta Security

---

## Autor

**Jefferson Carlos Sandoval Hernández**
Ingeniería en Sistemas Informáticos — Universidad de El Salvador
[github.com/jeffSH](https://github.com/jeffSH) · jeffsandoval016@gmail.com
