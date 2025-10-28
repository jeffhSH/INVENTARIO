package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.CaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoUnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;

import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class CaracteristicaFrm extends DefaultFrm<Caracteristica, Integer>implements Serializable {
    @Inject
    FacesContext facesContext;
    @Inject
    CaracteristicaDAO taDao;
    @Inject
    private TipoUnidadMedidaDAO tipoUnidadMedidaDAO;

    private List<TipoUnidadMedida> tiposUnidadMedida;

    private List<Caracteristica> listaCaracteristica;
    private Integer proximoId;
    @PostConstruct
    @Override
    protected void initDefaultFrm() {
        super.initDefaultFrm();
    }
    @Override
    protected InventarioDefaultDataAccess<Caracteristica> getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Caracteristicas";
    }

    @Override
    protected void inicializar() {
        try {
            listaCaracteristica = taDao.findRange(0, Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
       }
    public List<TipoUnidadMedida> getTiposUnidadMedida() {
        return tiposUnidadMedida;
    }


    protected Caracteristica crearInstanciaVacia() {
        Caracteristica Caracteristica = new Caracteristica();
        Caracteristica.setIdTipoUnidadMedida(new TipoUnidadMedida());
        return Caracteristica;
    }


    // ===== Implementaciones CRUD =====

    @Override
    protected String getIdAsText(Caracteristica r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }
public List<Caracteristica> getListaCompleta() {
    return taDao.getListaCompleta();
}


    @Override
    protected Caracteristica getIdByText(String id) {
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
    protected Integer getId(Caracteristica entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(Caracteristica entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(Caracteristica entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(Caracteristica entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected Caracteristica findById(Integer id) {
        return taDao.findById(id);
    }

    @Override
    protected List<Caracteristica> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<Caracteristica> getCaracteristica() {
        return listaCaracteristica;
    }

    public void setListaCaracteristica(List<Caracteristica> listaCaracteristica) {
        this.listaCaracteristica = listaCaracteristica;
    }


}
