package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoUnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;

@Named
@ViewScoped
public class TipoUnidadMedidaFrm extends DefaultFrm<TipoUnidadMedida, Integer> implements Serializable {
    @Inject
    FacesContext facesContext;
    @Inject
    TipoUnidadMedidaDAO taDao;
    @Inject
    private UnidadMedidaFrm unidadMedidaFrm;

    private List<TipoUnidadMedida> listaTipoUnidadMedida;
    private Integer proximoId;

    @Override
    protected InventarioDefaultDataAccess<TipoUnidadMedida> getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Tipo De Unidad De Medida";
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
    public List<TipoUnidadMedida> getlistaAtivos() {
        if (listaTipoUnidadMedida == null) {
            listaTipoUnidadMedida = taDao.getlistaActivos();
        }

        return listaTipoUnidadMedida;
    }

    public void setListaTipoUnidadMedida(List<TipoUnidadMedida> listaTipoUnidadMedida) {
        this.listaTipoUnidadMedida = listaTipoUnidadMedida;
    }


}
