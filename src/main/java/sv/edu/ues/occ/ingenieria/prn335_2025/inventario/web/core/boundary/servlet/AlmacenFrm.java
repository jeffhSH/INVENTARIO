package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.AlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

@Named
@ViewScoped
public class AlmacenFrm extends DefaultFrm<Almacen, Integer> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    AlmacenDAO almacenDAO;

    @Inject
    TipoAlmacenDAO tipoAlmacenDAO;

    private List<TipoAlmacen> listaTiposAlmacen;

    @Override
    protected InventarioDefaultDataAccess<Almacen> getDao() {
        return almacenDAO;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Almacén";
    }

    @Override
    protected void inicializar() {
        try {
            // Cargar la lista de tipos de almacén para el combo
            listaTiposAlmacen = tipoAlmacenDAO.findRange(0, Integer.MAX_VALUE);
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
    protected Almacen crearInstanciaVacia() {
        Almacen almacen = new Almacen();
        almacen.setActivo(true); // Por defecto activo
        return almacen;
    }

    @Override
    protected String getIdAsText(Almacen r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected Almacen getIdByText(String id) {
        if (id != null && this.model != null && !this.model.getWrappedData().isEmpty()) {
            try {
                Integer buscado = Integer.parseInt(id);
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
    protected Integer getId(Almacen entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(Almacen entidad) {
        almacenDAO.create(entidad);
    }

    @Override
    protected void modificar(Almacen entidad) {
        almacenDAO.update(entidad);
    }

    @Override
    protected void eliminar(Almacen entidad) {
        almacenDAO.delete(entidad);
    }

    @Override
    protected Almacen findById(Integer id) {
        return almacenDAO.findById(id);
    }

    @Override
    protected List<Almacen> findRange(int first, int pageSize) {
        return almacenDAO.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<TipoAlmacen> getListaTiposAlmacen() {
        return listaTiposAlmacen;
    }

    public void setListaTiposAlmacen(List<TipoAlmacen> listaTiposAlmacen) {
        this.listaTiposAlmacen = listaTiposAlmacen;
    }
}