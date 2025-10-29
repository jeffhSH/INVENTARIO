package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

@Named
@ViewScoped
public class TipoAlmacenFrm extends DefaultFrm<TipoAlmacen, Integer> implements Serializable {
    @Inject
    FacesContext facesContext;
    @Inject
    TipoAlmacenDAO taDao;

    private List<TipoAlmacen> listaTipoAlmacen;
    private Integer proximoId;

    @Override
    protected InventarioDefaultDataAccess<TipoAlmacen> getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Tipo de Almacén";
    }

    @Override
    protected void inicializar() {
        try {
            listaTipoAlmacen = taDao.findRange(0, Integer.MAX_VALUE);
            calcularProximoId();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
    }

    private void calcularProximoId() {
        try {
            Integer id = taDao.obtenerProximoId();
            proximoId = (id != null && id > 0) ? id : 1;
        } catch (Exception e) {
            e.printStackTrace();
            proximoId = 1;
        }
    }

    // ===== Implementaciones CRUD =====
    @Override
    protected TipoAlmacen crearInstanciaVacia() {
        return new TipoAlmacen();
    }
    @Override
    protected String getIdAsText(TipoAlmacen r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected TipoAlmacen getIdByText(String id) {
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
    protected Integer getId(TipoAlmacen entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(TipoAlmacen entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(TipoAlmacen entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(TipoAlmacen entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected TipoAlmacen findById(Integer id) {
        return taDao.findById(id);
    }

    @Override
    protected List<TipoAlmacen> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<TipoAlmacen> getListaTipoAlmacen() {
        return listaTipoAlmacen;
    }

    public void setListaTipoAlmacen(List<TipoAlmacen> listaTipoAlmacen) {
        this.listaTipoAlmacen = listaTipoAlmacen;
    }

    public Integer getProximoId() {
        return proximoId;
    }

    private AccionesFrm<TipoAlmacen> acciones;

    @PostConstruct
    public void init() {
        acciones = new AccionesFrm<>();
        acciones.setNuevoHandler(t -> btnNuevoHandler());
        acciones.setEditarHandler(t -> btnEditarHandler(registro));
        acciones.setEliminarHandler(t -> btnEliminarHandler(registro));
        acciones.setVolverHandler(() -> volver());
    }
}
