package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;

@Named
@ViewScoped
public class ProductoFrm extends DefaultFrm<Producto, UUID> implements Serializable {


    @Inject
    ProductoDAO taDao;

    private List<Producto> ListaProducto;

    @Override
    protected Object getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Productos";
    }

    @Override
    protected void inicializar() {
        try {
            ListaProducto = taDao.findRange(0, Integer.MAX_VALUE);
/// ///PRUEBA3
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
    protected Producto crearInstanciaVacia() {
        return new Producto();
    }
    @Override
    protected String getIdAsText(Producto r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected Producto getIdByText(String id) {
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
    protected UUID getId(Producto entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(Producto entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(Producto entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(Producto entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected Producto findById(UUID id) {
        return taDao.findById(id);
    }

    @Override
    protected List<Producto> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<Producto> getListaTProducton() {
        return ListaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.ListaProducto = ListaProducto;
    }


}
