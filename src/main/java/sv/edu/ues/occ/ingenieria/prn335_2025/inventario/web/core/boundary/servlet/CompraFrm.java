package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.logging.Level;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.CompraDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ProveedorDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Compra;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Proveedor;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

@Named
@ViewScoped
public class CompraFrm extends DefaultFrm<Compra, Integer> implements Serializable {
    private List<Compra> listaCompra;
    @Inject
    private CompraDAO compraDao;

    @Inject
    private ProveedorDAO proveedorDao;

    private List<Proveedor> proveedores;

    public CompraFrm() {
    }

    @Override
    protected void inicializar() {
        try {
            listaCompra = compraDao.findRange(0, Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
    }



    // ===== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS =====



    @Override
    protected String getNombreBeanConfig() {
        return "Gestión de Compras";
    }

    @Override
    protected Compra crearInstanciaVacia() {
        Compra compra = new Compra();
        compra.setProveedor(new Proveedor());
        compra.setFecha(OffsetDateTime.now());
        compra.setEstado("CREADA");
        return compra;
    }

    @Override
    protected Integer getId(Compra entidad) {
        return entidad != null ? entidad.getId() : null;
    }

    @Override
    protected String getIdAsText(Compra r) {
        return r != null && r.getId() != null ? r.getId().toString() : null;
    }

    @Override
    protected Compra getIdByText(String id) {
        if (id == null || id.trim().isEmpty()) return null;
        try {
            Integer idInt = Integer.parseInt(id.trim());  // ✅ Cambia Long por Integer
            return compraDao.findById(idInt);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected InventarioDefaultDataAccess<Compra> getDao() {
        return compraDao;
    }

    @Override
    protected void crear(Compra entidad) {
        compraDao.create(entidad);
    }

    @Override
    protected void modificar(Compra entidad) {
        compraDao.update(entidad);
    }

    @Override
    protected void eliminar(Compra entidad) {
        compraDao.delete(entidad);
    }

    @Override
    public Compra findById(Integer id) {
        return compraDao.findById(id);
    }

    @Override
    protected List<Compra> findRange(int first, int pageSize) {
        return compraDao.findRange(first, pageSize);
    }

    // ===== MÉTODOS ESPECÍFICOS PARA COMPRA =====

    /**
     * Obtener compras por estado
     */
    public List<Compra> getComprasPorEstado(String estado) {
        try {
            return compraDao.findByEstado(estado);
        } catch (Exception e) {
            log(Level.SEVERE, "Error obteniendo compras por estado: " + estado, e);
            return null;
        }
    }

    // ===== GETTERS Y SETTERS PARA LA VISTA =====

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * Handler para procesar compra
     */
    public void btnProcesarHandler() {
        if (registro != null) {
            registro.setEstado("PROCESADA");
            compraDao.update(registro);
            addMsg(FacesMessage.SEVERITY_INFO, "Éxito", "Compra procesada correctamente");
        }
    }

    /**
     * Handler para cancelar compra
     */

}