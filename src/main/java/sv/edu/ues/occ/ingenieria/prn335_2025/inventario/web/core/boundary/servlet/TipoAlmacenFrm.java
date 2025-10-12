package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

@Named
@ViewScoped
public class TipoAlmacenFrm extends DefaultFrm<TipoAlmacen, Integer> implements Serializable {

    @Inject
    TipoAlmacenDAO taDao;

    private List<TipoAlmacen> listaTipoAlmacen;
    private Integer proximoId;

    @Override
    protected Object getDao() {
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
}
