package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoUnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;

@Named
@ViewScoped
public class TipoUnidadMedidaFrm extends DefaultFrm<TipoUnidadMedida, Integer> implements Serializable {
    @Inject
    FacesContext facesContext;
    @Inject
    TipoUnidadMedidaDAO taDao;

    private List<TipoUnidadMedida> listaTipoUnidadMedida;
    private Integer proximoId;

    @Override
    protected Object getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Tipo De Unidad De Medida";
    }

    @Override
    protected void inicializar() {
        try {
            listaTipoUnidadMedida = taDao.findRange(0, Integer.MAX_VALUE);
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
    protected TipoUnidadMedida crearInstanciaVacia() {
        return new TipoUnidadMedida();
    }
    @Override
    protected String getIdAsText(TipoUnidadMedida r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected TipoUnidadMedida getIdByText(String id) {
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
    protected Integer getId(TipoUnidadMedida entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(TipoUnidadMedida entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(TipoUnidadMedida entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(TipoUnidadMedida entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected TipoUnidadMedida findById(Integer id) {
        return taDao.findById(id);
    }

    @Override
    protected List<TipoUnidadMedida> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<TipoUnidadMedida> getlistaTipoUnidadMedida() {
        return listaTipoUnidadMedida;
    }

    public void setListaTipoUnidadMedida(List<TipoUnidadMedida> listaTipoUnidadMedida) {
        this.listaTipoUnidadMedida = listaTipoUnidadMedida;
    }

    public Integer getProximoId() {
        return proximoId;
    }
}
