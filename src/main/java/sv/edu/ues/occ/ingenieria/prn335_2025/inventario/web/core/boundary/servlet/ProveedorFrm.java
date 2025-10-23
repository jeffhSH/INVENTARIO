package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;


import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet.DefaultFrm;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ProveedorDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Proveedor;

@Named
@ViewScoped
public class ProveedorFrm extends DefaultFrm<Proveedor, Integer> implements Serializable {

    @Inject
    private ProveedorDAO proveedorDao;

    public ProveedorFrm() {
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Proveedores";
    }

    @Override
    protected InventarioDefaultDataAccess<Proveedor> getDao() {
        return proveedorDao;
    }

    @Override
    protected Proveedor crearInstanciaVacia() {
        return new Proveedor();
    }

    @Override
    protected Integer getId(Proveedor entidad) {
        return entidad != null ? entidad.getId() : null;
    }

    @Override
    protected void crear(Proveedor entidad) {
        proveedorDao.create(entidad);
    }

    @Override
    protected void modificar(Proveedor entidad) {
        proveedorDao.update(entidad);
    }

    @Override
    protected void eliminar(Proveedor entidad) {
        proveedorDao.delete(entidad);
    }

    @Override
    protected Proveedor findById(Integer id) {
        return proveedorDao.findById(id);
    }

    @Override
    protected List<Proveedor> findRange(int first, int pageSize) {
        return proveedorDao.findRange(first, pageSize);
    }

    @Override
    protected String getIdAsText(Proveedor r) {
        return r != null && r.getId() != null ? r.getId().toString() : null;
    }

    @Override
    protected Proveedor getIdByText(String id) {
        if (id != null && !id.trim().isEmpty()) {
            try {
                return proveedorDao.findById(Integer.valueOf(id.trim()));
            } catch (NumberFormatException nfe) {
                // Log opcional
            }
        }
        return null;
    }
}