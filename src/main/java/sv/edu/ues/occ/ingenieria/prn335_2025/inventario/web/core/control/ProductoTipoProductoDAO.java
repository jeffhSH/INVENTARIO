package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.ProductoTipoProducto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Stateless
public class ProductoTipoProductoDAO extends InventarioDefaultDataAccess<ProductoTipoProducto> {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public ProductoTipoProductoDAO() {
        super(ProductoTipoProducto.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // Métodos específicos para ProductoTipoProducto

    /**
     * Busca relaciones por producto
     */
    public List<ProductoTipoProducto> findByProducto(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto",
                        ProductoTipoProducto.class)
                .setParameter("idProducto", idProducto)
                .getResultList();
    }
    public List<ProductoTipoProducto> findByProduct(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id =:idProducto")
                .setParameter("idProducto", idProducto)
                .getResultList();
    }

    /**
     * Busca relaciones por tipo de producto
     */
    public List<ProductoTipoProducto> findByTipoProducto(UUID idTipoProducto) {
        if (idTipoProducto == null) {
            throw new IllegalArgumentException("ID Tipo Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto",
                        ProductoTipoProducto.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }

    /**
     * Busca relaciones activas por producto
     */
    public List<ProductoTipoProducto> findActivosByProducto(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                        ProductoTipoProducto.class)
                .setParameter("idProducto", idProducto)
                .getResultList();
    }
    @Override
    public void create(ProductoTipoProducto registro) throws IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        try {
            // Generar UUID si no existe
            if (registro.getId() == null) {
                registro.setId(UUID.randomUUID());
            }

            // Asegurar que tenga fecha de creación
            if (registro.getFechaCreacion() == null) {
                registro.setFechaCreacion(OffsetDateTime.now());
            }

            System.out.println("DEBUG DAO - Creando relación: " + registro.getId());
            System.out.println("DEBUG DAO - Producto: " + (registro.getIdProducto() != null ? registro.getIdProducto().getId() : "null"));
            System.out.println("DEBUG DAO - Tipo Producto: " + (registro.getIdTipoProducto() != null ? registro.getIdTipoProducto().getId() : "null"));

            em.persist(registro);
            em.flush(); // Forzar sincronización con la BD

            System.out.println("DEBUG DAO - Relación creada exitosamente");

        } catch (Exception e) {
            System.out.println("DEBUG DAO - Error al crear: " + e.getMessage());
            throw new RuntimeException("Error al crear ProductoTipoProducto", e);
        }
    }
    /**
     * Busca relaciones activas por tipo de producto
     */
    public List<ProductoTipoProducto> findActivosByTipoProducto(UUID idTipoProducto) {
        if (idTipoProducto == null) {
            throw new IllegalArgumentException("ID Tipo Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                        ProductoTipoProducto.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }

    /**
     * Verifica si existe una relación entre un producto y un tipo de producto
     */
    public boolean existsRelation(UUID idProducto, UUID idTipoProducto) {
        if (idProducto == null || idTipoProducto == null) {
            throw new IllegalArgumentException("IDs no pueden ser nulos");
        }

        Long count = em.createQuery(
                        "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.idTipoProducto.id = :idTipoProducto",
                        Long.class)
                .setParameter("idProducto", idProducto)
                .setParameter("idTipoProducto", idTipoProducto)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Desactiva todas las relaciones de un producto
     */
    public int deactivateByProducto(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idProducto.id = :idProducto")
                .setParameter("idProducto", idProducto)
                .executeUpdate();
    }


    /**
     * Desactiva todas las relaciones de un tipo de producto
     */
    public int deactivateByTipoProducto(UUID idTipoProducto) {
        if (idTipoProducto == null) {
            throw new IllegalArgumentException("ID Tipo Producto no puede ser nulo");
        }

        return em.createQuery(
                        "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idTipoProducto.id = :idTipoProducto")
                .setParameter("idTipoProducto", idTipoProducto)
                .executeUpdate();
    }

    /**
     * Cuenta las relaciones activas por producto
     */
    public long countActivosByProducto(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                        Long.class)
                .setParameter("idProducto", idProducto)
                .getSingleResult();
    }

    /**
     * Cuenta las relaciones activas por tipo de producto
     */
    public long countActivosByTipoProducto(UUID idTipoProducto) {
        if (idTipoProducto == null) {
            throw new IllegalArgumentException("ID Tipo Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                        Long.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getSingleResult();
    }
}