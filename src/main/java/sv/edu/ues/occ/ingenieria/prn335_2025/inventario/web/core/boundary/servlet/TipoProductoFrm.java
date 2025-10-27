package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

@Named
@ViewScoped
public class TipoProductoFrm extends DefaultFrm<TipoProducto, Long> implements Serializable {

    @Inject
    private TipoProductoDAO dao;

    private List<TipoProducto> tiposPadre; // Para el combo de tipos padre

    @PostConstruct
    @Override
    protected void initDefaultFrm() {
        super.initDefaultFrm();
    }

    @Override
    protected void inicializar() {
        // Cargar tipos padre para combos
        cargarTiposPadre();
    }

    private void cargarTiposPadre() {
        try {
            this.tiposPadre = dao.findTiposPadre();
        } catch (Exception e) {
            log(Level.SEVERE, "Error cargando tipos padre", e);
            addMsg(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los tipos padre");
        }
    }


    // ===== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS =====

    @Override
    protected InventarioDefaultDataAccess<TipoProducto> getDao() {
        return dao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Gestión de Tipos de Producto";
    }

    @Override
    protected TipoProducto crearInstanciaVacia() {
        TipoProducto tipo = new TipoProducto();
        tipo.setActivo(true); // Por defecto activo

        return tipo;
    }

    @Override
    protected Long getId(TipoProducto entidad) {
        return entidad != null ? entidad.getId() : null;
    }

    @Override
    protected String getIdAsText(TipoProducto r) {
        return r != null && r.getId() != null ? r.getId().toString() : null;
    }

    @Override
    protected TipoProducto getIdByText(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            Long idLong = Long.parseLong(id.trim());
            return dao.findById(idLong);
        } catch (NumberFormatException e) {
            log(Level.WARNING, "ID inválido: " + id, e);
            return null;
        }
    }

    @Override
    protected void crear(TipoProducto entidad) {
        // Validar nombre único antes de crear
        if (dao.existeNombre(entidad.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de producto con el nombre: " + entidad.getNombre());
        }
        dao.create(entidad);
        cargarTiposPadre(); // Recargar combos después de crear
    }

    @Override
    protected void modificar(TipoProducto entidad) {
        // Validar nombre único excluyendo el actual
        if (dao.existeNombreExcluyendoId(entidad.getNombre(), entidad.getId())) {
            throw new IllegalArgumentException("Ya existe otro tipo de producto con el nombre: " + entidad.getNombre());
        }
        dao.update(entidad);
        cargarTiposPadre(); // Recargar combos después de modificar
    }

    @Override
    protected void eliminar(TipoProducto entidad) {
        // Validar si tiene subtipos antes de eliminar
        long cantidadSubtipos = dao.contarSubtipos(entidad);
        if (cantidadSubtipos > 0) {
            throw new IllegalArgumentException("No se puede eliminar el tipo de producto porque tiene " + cantidadSubtipos + " subtipo(s) asociado(s)");
        }
        dao.delete(entidad);
        cargarTiposPadre(); // Recargar combos después de eliminar
    }

    @Override
    protected TipoProducto findById(Long id) {
        return dao.findById(id);
    }

    @Override
    protected List<TipoProducto> findRange(int first, int pageSize) {
        return dao.findRange(first, pageSize);
    }

    // ===== MÉTODOS ESPECÍFICOS PARA TIPO PRODUCTO =====

    /**
     * Obtiene los subtipos del tipo de producto actual
     */
    public List<TipoProducto> getSubtipos() {
        if (registro != null && registro.getId() != null) {
            try {
                return dao.findSubtiposById(registro.getId());
            } catch (Exception e) {
                log(Level.SEVERE, "Error obteniendo subtipos", e);
            }
        }
        return null;
    }

    /**
     * Verifica si el tipo de producto actual tiene subtipos
     */
    public boolean isTieneSubtipos() {
        if (registro != null && registro.getId() != null) {
            try {
                return dao.contarSubtipos(registro) > 0;
            } catch (Exception e) {
                log(Level.SEVERE, "Error verificando subtipos", e);
            }
        }
        return false;
    }

    // ===== GETTERS Y SETTERS PARA LA VISTA =====

    public List<TipoProducto> getTiposPadre() {
        return dao.findTiposPadre();
    }

    public void setTiposPadre(List<TipoProducto> tiposPadre) {
        this.tiposPadre = tiposPadre;
    }

    /**
     * Método para limpiar la selección del tipo padre
     */
    public void limpiarTipoPadre() {
        if (registro != null) {
            registro.setIdTipoProductoPadre(null);
        }
    }

    /**
     * Handler específico para nuevo tipo de producto
     */
    public void btnNuevoTipoHandler() {
        btnNuevoHandler(null);
        cargarTiposPadre(); // Asegurar que los combos estén actualizados
    }

    /**
     * Handler específico para editar tipo de producto
     */
    public void btnEditarTipoHandler(TipoProducto tipo) {
        btnEditarHandler(tipo);
        cargarTiposPadre(); // Asegurar que los combos estén actualizados
    }
}