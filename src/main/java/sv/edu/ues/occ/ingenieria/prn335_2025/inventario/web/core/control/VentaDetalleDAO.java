package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.VentaDetalle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
public class VentaDetalleDAO extends InventarioDefaultDataAccess<VentaDetalle> implements Serializable {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public VentaDetalleDAO() {
        super(VentaDetalle.class);
    }

    // Constructor para testing (Mockito)
    public VentaDetalleDAO(EntityManager em) {
        super(VentaDetalle.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(VentaDetalle registro) throws IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }
            // Generar UUID si no existe
            if (registro.getId() == null) {
                registro.setId(UUID.randomUUID());
            }

            em.persist(registro);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear el registro", ex);
        }
    }

    /**
     * Busca todos los detalles de venta por ID de venta
     */
    public List<VentaDetalle> findByIdVenta(UUID idVenta) {
        return em.createQuery(
                        "SELECT vd FROM VentaDetalle vd WHERE vd.idVenta.id = :idVenta ORDER BY vd.idProducto.nombreProducto",
                        VentaDetalle.class)
                .setParameter("idVenta", idVenta)
                .getResultList();
    }

    /**
     * Calcula el subtotal (precio * cantidad) para un detalle de venta
     * Este método va en el DAO porque realiza operaciones con los datos
     */
    public BigDecimal calcularSubtotal(VentaDetalle detalle) {
        if (detalle == null || detalle.getCantidad() == null || detalle.getPrecio() == null) {
            return BigDecimal.ZERO;
        }
        return detalle.getCantidad().multiply(detalle.getPrecio());
    }

    /**
     * Calcula el total de una venta sumando todos los subtotales de sus detalles
     */
    public BigDecimal calcularTotalVenta(UUID idVenta) {
        List<VentaDetalle> detalles = findByIdVenta(idVenta);

        BigDecimal total = BigDecimal.ZERO;
        for (VentaDetalle detalle : detalles) {
            total = total.add(calcularSubtotal(detalle));
        }

        return total;
    }
    public List<VentaDetalle> findAll(UUID id) {
        return em.createQuery(
                        "SELECT t FROM VentaDetalle t WHERE t.idVenta.id = :id ORDER BY t.id",
                        VentaDetalle.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Busca detalles de venta por ID de venta con sus productos cargados (evita LazyLoading)
     */
    public List<VentaDetalle> findByIdVentaWithProductos(UUID idVenta) {
        return em.createQuery(
                        "SELECT vd FROM VentaDetalle vd " +
                                "LEFT JOIN FETCH vd.idProducto " +
                                "WHERE vd.idVenta.id = :idVenta " +
                                "ORDER BY vd.idProducto.nombreProducto",
                        VentaDetalle.class)
                .setParameter("idVenta", idVenta)
                .getResultList();
    }

    /**
     * Busca detalles de venta activos por ID de venta
     */
    public List<VentaDetalle> findActivosByIdVenta(UUID idVenta) {
        return em.createQuery(
                        "SELECT vd FROM VentaDetalle vd " +
                                "WHERE vd.idVenta.id = :idVenta AND vd.estado = 'ACTIVO' " +
                                "ORDER BY vd.idProducto.nombreProducto",
                        VentaDetalle.class)
                .setParameter("idVenta", idVenta)
                .getResultList();
    }
}