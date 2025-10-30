package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.VentaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.VentaDetalleDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Venta;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.VentaDetalle;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class VentaDetalleFrm extends DefaultFrm<VentaDetalle, UUID> implements Serializable {

    @Inject
    VentaDetalleDAO taDao;
    @Inject
   VentaFrm ventaFrm;
    @Inject
    VentaDAO ventaDAO;

    private List<VentaDetalle> ListaVentaDetalle;
    private UUID idVentaSeleccionada; // Para filtrar por venta específica

    @Override
    protected InventarioDefaultDataAccess<VentaDetalle> getDao() {
        return taDao;
    }
    @PostConstruct
    public void init() {

    }

    @Override
    protected String getNombreBeanConfig() {
        return "Detalles de Venta";
    }

    @Override
    protected void inicializar() {
        try {
            if (idVentaSeleccionada != null) {
                // Cargar detalles de una venta específica
                ListaVentaDetalle = taDao.findByIdVenta(idVentaSeleccionada);
            } else {
                // Cargar todos los detalles
                ListaVentaDetalle = taDao.findRange(0, Integer.MAX_VALUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
    }

    // ===== Implementaciones CRUD =====
    @Override
    protected VentaDetalle crearInstanciaVacia() {
        VentaDetalle detalle = new VentaDetalle();

        // Asignar la venta actual del registro principal
            if (ventaFrm.getRegistro() != null && ventaFrm.getRegistro().getId() != null) {
                detalle.setIdVenta(ventaFrm.getRegistro());
                System.out.println("✅ Venta asignada en crearInstanciaVacia: " + ventaFrm.getRegistro().getId());
            }

        // NO crear un nuevo Producto - dejar null para que el usuario seleccione uno
        // detalle.setIdProducto(new Producto()); // ← ESTA LÍNEA CAUSA EL ERROR

        detalle.setCantidad(BigDecimal.ZERO);
        detalle.setPrecio(BigDecimal.ZERO);
        detalle.setEstado("ACTIVO");
        return detalle;
    }

    @Override
    protected String getIdAsText(VentaDetalle r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected VentaDetalle getIdByText(String id) {
        if (id != null && this.model != null && !this.model.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.model.getWrappedData().stream()
                        .filter(r -> r.getId() != null && r.getId().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (IllegalArgumentException e) {
                System.err.println("ID no es un UUID válido: " + id);
                return null;
            }
        }
        return null;
    }

    @Override
    protected UUID getId(VentaDetalle entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(VentaDetalle entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(VentaDetalle entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(VentaDetalle entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected VentaDetalle findById(UUID id) {
        return taDao.findById(id);
    }

    @Override
    protected List<VentaDetalle> findRange(int first, int pageSize) {
        if (idVentaSeleccionada != null) {
            return taDao.findByIdVenta(idVentaSeleccionada);
        }
        return taDao.findRange(first, pageSize);
    }

    // ===== MÉTODOS ESPECÍFICOS PARA VENTA_DETALLE =====

    /**
     * Busca detalles por ID de venta
     */
    public List<VentaDetalle> findByIdVenta(UUID idVenta) {
        return taDao.findByIdVenta(idVenta);
    }

    /**
     * Busca detalles por ID de venta con productos cargados
     */
    public List<VentaDetalle> findByIdVentaWithProductos(UUID idVenta) {
        return taDao.findByIdVentaWithProductos(idVenta);
    }
    public List<VentaDetalle> findAll(UUID id) {
        return taDao.findAll(id);
    }

    /**
     * Calcula el subtotal de un detalle (precio * cantidad)
     */
    public BigDecimal calcularSubtotal(VentaDetalle detalle) {
        return taDao.calcularSubtotal(detalle);
    }

    /**
     * Calcula el total de una venta sumando todos sus detalles
     */
    public BigDecimal calcularTotalVenta(UUID idVenta) {
        return taDao.calcularTotalVenta(idVenta);
    }

    /**
     * Busca detalles activos por ID de venta
     */
    public List<VentaDetalle> findActivosByIdVenta(UUID idVenta) {
        return taDao.findActivosByIdVenta(idVenta);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====

    public List<VentaDetalle> getListaVentaDetalle() {
        return ListaVentaDetalle;
    }

    public void setListaVentaDetalle(List<VentaDetalle> listaVentaDetalle) {
        this.ListaVentaDetalle = listaVentaDetalle;
    }

    public UUID getIdVentaSeleccionada() {
        return idVentaSeleccionada;
    }

    public void setIdVentaSeleccionada(UUID idVentaSeleccionada) {
        this.idVentaSeleccionada = idVentaSeleccionada;
        // Recargar datos cuando se cambia la venta seleccionada
        if (idVentaSeleccionada != null) {
            inicializar();
        }
    }

    /**
     * Método para obtener el subtotal de forma conveniente en XHTML
     */
    public BigDecimal getSubtotal(VentaDetalle detalle) {
        return calcularSubtotal(detalle);
    }
}