package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.VentaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Venta;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Named
@ViewScoped
public class VentaFrm extends DefaultFrm<Venta, UUID> implements Serializable {
    @Inject
    VentaDAO taDao;

    private List<Venta> ListaVenta;

    @Override
    protected InventarioDefaultDataAccess<Venta> getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Ventas";
    }

    @Override
    protected void inicializar() {
        try {
            ListaVenta = taDao.findRange(0, Integer.MAX_VALUE);

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
    protected Venta crearInstanciaVacia() {
        Venta venta = new Venta();
        venta.setFecha(OffsetDateTime.now());
        venta.setIdCliente(new Cliente());
        return venta;
    }
    @Override
    protected String getIdAsText(Venta r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected Venta getIdByText(String id) {
        if (id != null && this.model != null && !this.model.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.model.getWrappedData().stream()
                        .filter(r -> r.getId() != null && r.getId().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (NumberFormatException e) {
                System.err.println("ID no es un número válido: " + id);
                return null;
            }
        }
        return null;
    }

    @Override
    protected UUID getId(Venta entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(Venta entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(Venta entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(Venta entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected Venta findById(UUID id) {
        return taDao.findById(id);
    }

    @Override
    protected List<Venta> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<Venta> getListaTProducton() {
        return ListaVenta;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.ListaVenta = ListaVenta;
    }


}
