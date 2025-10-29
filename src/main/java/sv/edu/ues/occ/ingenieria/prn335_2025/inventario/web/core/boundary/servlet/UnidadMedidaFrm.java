package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.ESTADO_CRUD;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoUnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.UnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;

@Named
@ViewScoped

public class UnidadMedidaFrm extends DefaultFrm<UnidadMedida, Integer> implements Serializable {
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    @Inject
    private UnidadMedidaDAO taDao;
    @Inject
    private TipoUnidadMedidaFrm tipoUnidadMedidaFrm;
    private List<UnidadMedida> listaUnidadMedida;

    @Inject
    private TipoUnidadMedidaDAO tipoUnidadMedidaDAO;


    @Override
    protected InventarioDefaultDataAccess<UnidadMedida> getDao() {
        return taDao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Unidad De Medida";
    }

    @Override
    protected void inicializar() {
        try {
            listaUnidadMedida = taDao.findRange(0, Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
    }
    @PostConstruct
    @Override
    protected void initDefaultFrm() {
        super.initDefaultFrm();
        if (getRegistro() == null) {
            setRegistro(crearInstanciaVacia());
        }
    }
    @Override
    public UnidadMedida getRegistro() {
        if (super.getRegistro() == null) {
            setRegistro(crearInstanciaVacia());
        }
        return super.getRegistro();
    }
    public  List<UnidadMedida> getListaCompleta(){
        return taDao.getListaCompleta();
    }

    // ===== Implementaciones CRUD =====
    @Override
    protected UnidadMedida crearInstanciaVacia() {
        UnidadMedida unidadMedida = new UnidadMedida();

        if (tipoUnidadMedidaFrm.getRegistro() != null && tipoUnidadMedidaFrm.getRegistro().getId() != null) {
            TipoUnidadMedida tipoPersistido = tipoUnidadMedidaDAO.findById(tipoUnidadMedidaFrm.getRegistro().getId());
            if (tipoPersistido != null) {
                unidadMedida.setIdTipoUnidadMedida(tipoPersistido);
                System.out.println("✅ Tipo asignado en crearInstanciaVacia: " + tipoPersistido.getNombre());
            }
        }

        return unidadMedida;
    }



    public void setTipoUnidadMedida(TipoUnidadMedida tipo) {
        if (this.registro != null && tipo != null) {
            this.registro.setIdTipoUnidadMedida(tipo);
        }
    }

    @Override
    protected String getIdAsText(UnidadMedida r) {
        if (r != null && r.getId() != null) {
            return r.getId().toString();
        }
        return null;
    }

    @Override
    protected UnidadMedida getIdByText(String id) {
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


    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    @Override
    protected Integer getId(UnidadMedida entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(UnidadMedida entidad) {
        taDao.create(entidad);
    }

    @Override
    protected void modificar(UnidadMedida entidad) {
        taDao.update(entidad);
    }

    @Override
    protected void eliminar(UnidadMedida entidad) {
        taDao.delete(entidad);
    }

    @Override
    protected UnidadMedida findById(Integer id) {
        return taDao.findById(id);
    }

    @Override
    protected List<UnidadMedida> findRange(int first, int pageSize) {
        return taDao.findRange(first, pageSize);
    }

        public List<UnidadMedida> getListaUnidadMedida(Integer idTipoMedida) {
            return taDao.buscarPorTipo(idTipoMedida);
        }

    public List<UnidadMedida> getlistaUnidadMedida() {
        return listaUnidadMedida;
    }

    public void setListaUnidadMedida(List<UnidadMedida> listaUnidadMedida) {
        this.listaUnidadMedida = listaUnidadMedida;
    }


}
