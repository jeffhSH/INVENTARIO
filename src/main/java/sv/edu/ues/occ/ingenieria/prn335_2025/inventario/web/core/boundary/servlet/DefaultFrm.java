package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.ESTADO_CRUD;

public abstract class DefaultFrm<T, K extends Serializable> implements Serializable {

     ESTADO_CRUD estado = ESTADO_CRUD.NADA;
    protected String nombreBean;
    protected List<T> registros;
    protected LazyDataModel<T> model;
    protected T registro;
    protected int pageSize = 10;
    protected boolean mostrarFormulario = false;
    protected boolean editionMode = false;
    protected boolean pnlDetalle = false;
    abstract protected String getIdAsText(T r);
    abstract protected T getIdByText(String id);

    //protected T selectedRow;




    /** Cada subclase debe entregar su DAO concreto */
    protected abstract Object getDao(); // mantiene flexibilidad en jerarquías de DAO

    /** Nombre que se mostrará en la vista (título del formulario) */
    protected abstract String getNombreBeanConfig();

    @PostConstruct
    protected void initDefaultFrm() {
        try {

            this.nombreBean = getNombreBeanConfig();
            this.registro   = crearInstanciaVacia();
            configurarModeloLazy();
            inicializar(); // Gancho opcional para la subclase
        } catch (Exception e) {
            log(Level.SEVERE, "Error inicializando el formulario", e);
            addMsg(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }

    }

    public void selectionHandler(SelectEvent<T> r) {
        if (r != null) {
            this.registro = r.getObject();
            this.editionMode = true;
            this.estado = ESTADO_CRUD.MODIFICAR;
            this.pnlDetalle = true;
            this.mostrarFormulario = false;
            System.out.println("✅ Registro seleccionado: " + this.registro);
        } else {
            System.out.println("⚠ Evento de selección nulo");
            System.out.println("✅ ID del registro: " + getIdAsText(this.registro));
        }
    }
    /** Gancho opcional para la subclase (carga inicial, combos, etc.) */
    protected void inicializar() { /* opcional */ }

    // ===== LazyDataModel =====
    private void configurarModeloLazy() {

        this.model = new LazyDataModel<T>() {
            @Override
            public String getRowKey(T object) {
                if (object != null) {
                    try {
                        return getIdAsText(object);
                    } catch (Exception e) {
                        Logger.getLogger(DefaultFrm.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                return null;
            }


            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null) {
                    try {
                        return getIdByText(rowKey);
                    } catch (Exception e) {
                        Logger.getLogger(DefaultFrm.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                return null;
            }
            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return DefaultFrm.this.count(filterBy);
            }

            @Override
            public List<T> load(int first, int pageSize,
                                Map<String, SortMeta> sortBy,
                                Map<String, FilterMeta> filterBy) {
                try {
                    List<T> data = findRange(first, pageSize);
                    setRowCount(count(filterBy));
                    return data;
                } catch (Exception e) {
                    log(Level.SEVERE, "Error cargando datos (lazy)", e);
                    addMsg(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage());
                    return Collections.emptyList();
                }
            }
        };
        this.model.setPageSize(this.pageSize);
    }

    /** Conteo de registros (sin filtros avanzados por ahora) */
    public int count(Map<String, FilterMeta> filterBy) {
        try {
            // Se asume que el DAO concreto expone count()
            return (int) getDao().getClass().getMethod("count").invoke(getDao());
        } catch (Exception e) {
            Logger.getLogger(DefaultFrm.class.getName())
                    .log(Level.SEVERE, "Error al contar los registros", e);
            return 0;
        }
    }

    // ===== Handlers por defecto =====
    public void btnNuevoHandler(ActionEvent e) {
        this.registro = crearInstanciaVacia();
        this.editionMode = false;
        this.estado = ESTADO_CRUD.CREAR;
        this.mostrarFormulario = true;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo", "Formulario listo"));

    }
    public void rowUnselectHandler(UnselectEvent event) {
        // Restablece la propiedad editionMode cuando se deselecciona la fila

        this.editionMode = false;  // Ya no hay una fila seleccionada
        this.registro = null;      // Limpia el registro seleccionado
    }

    public void btnEditarHandler(T fila) {
        try {
            if (fila == null) {
                addMsg(FacesMessage.SEVERITY_WARN, "Atención", "Seleccione un registro");
                return;
            }
            K id = getId(fila);
            if (id == null) {
                addMsg(FacesMessage.SEVERITY_ERROR, "Error", "El ID del registro es nulo");
                return;
            }
            this.registro = findById(id);
            this.editionMode = true;
            this.mostrarFormulario = true;
        } catch (Exception ex) {
            log(Level.SEVERE, "Error preparando edición", ex);
            addMsg(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage());
        }
    }

    public void btnGuardarHandler(ActionEvent e) {
        try {
            if (!editionMode) {
                crear(this.registro);
                addMsg(FacesMessage.SEVERITY_INFO, "Éxito", "Registro creado correctamente");
            } else {
                pnlDetalle=false;
                        modificar(this.registro);
                addMsg(FacesMessage.SEVERITY_INFO, "Éxito", "Registro modificado correctamente");
            }
            recargar();
            cancelarEdicion();
        } catch (Exception ex) {
            log(Level.SEVERE, "Error al guardar", ex);
            addMsg(FacesMessage.SEVERITY_ERROR, "Error al guardar", ex.getMessage());
        }
    }

    public void btnEliminarHandler(T fila) {
        try {
            if (fila == null) {
                addMsg(FacesMessage.SEVERITY_WARN, "Atención", "Seleccione un registro");
                return;
            }
            eliminar(fila);
            this.editionMode = false;
            this.pnlDetalle=false;
            addMsg(FacesMessage.SEVERITY_INFO, "Éxito", "Registro eliminado");
            recargar();
        } catch (Exception ex) {
            log(Level.SEVERE, "Error al eliminar", ex);
            addMsg(FacesMessage.SEVERITY_ERROR, "Error al eliminar", ex.getMessage());
        }
    }

    public void btnCancelarHandler(ActionEvent e) {
        cancelarEdicion();
    }
public void volver(){
        this.pnlDetalle=false;
        this.estado = ESTADO_CRUD.NADA;

}
    protected void cancelarEdicion() {

            this.estado = ESTADO_CRUD.NADA;
        this.mostrarFormulario = false;

    }

    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected void addMsg(FacesMessage.Severity sev, String resumen, String detalle) {
        getFacesContext().addMessage(null, new FacesMessage(sev, resumen, detalle));
    }

    protected void log(Level level, String msg, Throwable t) {
        Logger.getLogger(getClass().getName()).log(level, msg, t);
    }

    protected void recargar() {
        try {
            if (this.model != null) {
                this.model.setRowCount(count(Collections.emptyMap()));
            }
        } catch (Exception e) {
            log(Level.WARNING, "No se pudo recalcular el total", e);
        }
    }

    // ===== GETTERS Y SETTERS PÚBLICOS para JSF =====
    public String getNombreBean() {
        return nombreBean;
    }
    public ESTADO_CRUD getEstado() {
        return this.estado;
    }
    public List<T> getRegistros() {
        return registros;
    }

    public LazyDataModel<T> getModel() {
        return model;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isMostrarFormulario() {
        return mostrarFormulario;
    }

    public void setMostrarFormulario(boolean mostrarFormulario) {
        this.mostrarFormulario = mostrarFormulario;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public boolean isEditionMode() {
        return editionMode;
    }
    public boolean isPnlDetalle() {
        return pnlDetalle;
    }
    public void setPnlDetalle(boolean pnlDetalle) {
        this.pnlDetalle = pnlDetalle;
    }

    public void setEditionMode(boolean editionMode) {
        this.editionMode = editionMode;
    }

    // ===== MÉTODOS ABSTRACTOS DE CRUD =====
    protected abstract T crearInstanciaVacia();
    protected abstract K getId(T entidad);
    protected abstract void crear(T entidad);
    protected abstract void modificar(T entidad);
    protected abstract void eliminar(T entidad);
    protected abstract T findById(K id);

    protected abstract List<T> findRange(int first, int pageSize);
}
