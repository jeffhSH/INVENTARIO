package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.AlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

@Named
@ViewScoped
public class AlmacenFrm extends DefaultFrm<Almacen, Integer> implements Serializable {

    @Inject
    AlmacenDAO AlmacenDAO;

    @Inject
    TipoAlmacenDAO tipoAlmacenDAO;

    private List<Almacen> listaAlmacenes;

    @Override
    protected void inicializar(){
        try{
            AlmacenDAO.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
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
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            Long idLong = Long.parseLong(id.trim());
            return AlmacenDAO.findById(idLong);
        } catch (NumberFormatException e) {
            log(Level.WARNING, "ID inválido: " + id, e);
            return null;
        }
    }

    @Override
    protected InventarioDefaultDataAccess<Almacen> getDao() {return AlmacenDAO;}

    @Override
    protected String getNombreBeanConfig() {return "Almacenes";}

    //CRUD
    @Override
    protected Almacen crearInstanciaVacia() {return new Almacen();}
    @Override
    protected Integer getId(Almacen entidad) {return entidad.getId();}
    @Override
    protected void crear(Almacen entidad) {AlmacenDAO.create(entidad);}
    @Override
    protected void modificar(Almacen entidad) {AlmacenDAO.update(entidad);}
    @Override
    protected void eliminar(Almacen entidad) {AlmacenDAO.delete(entidad);}
    @Override
    protected Almacen findById(Integer id) {return AlmacenDAO.findById(id);}
    @Override
    protected List<Almacen> findRange(int first, int pageSize) {return AlmacenDAO.findRange(first, pageSize);}

    //Getters and Setters
    public List<Almacen> getListaAlmacenes() {
        return listaAlmacenes;
    }
    public void setListaAlmacenes(List<Almacen> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public List<TipoAlmacen> completarTipoAlmacen(String consulta) throws Exception {
        return tipoAlmacenDAO.findLikeConsulta(consulta);
    }
}
