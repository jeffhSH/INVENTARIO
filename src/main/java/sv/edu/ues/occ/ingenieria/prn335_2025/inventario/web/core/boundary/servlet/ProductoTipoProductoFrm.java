package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ProductoTipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.ProductoTipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Named
@SessionScoped
public class ProductoTipoProductoFrm extends DefaultFrm<ProductoTipoProducto, UUID> implements Serializable {
    boolean formularioTipo = false;

    @Override
    public void btnNuevoHandler(ActionEvent e) {
        this.formularioTipo = true;
        super.btnNuevoHandler(e);
    }

    public void btnCancelarTipoHandler(ActionEvent e) {
        this.formularioTipo = false;
        super.btnCancelarHandler(e);
    }
    @Inject
    private ProductoTipoProductoDAO dao;

    @Inject
    private ProductoFrm productoFrm;

    @Inject
    private TipoProductoFrm tipoProductoFrm;

    private Producto productoSeleccionado;
    private TipoProducto tipoProductoSeleccionado;

    @PostConstruct
    @Override
    protected void initDefaultFrm() {
        super.initDefaultFrm();
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Producto - Tipo Producto";
    }

    @Override
    protected InventarioDefaultDataAccess<ProductoTipoProducto> getDao() {
        return dao;
    }

    @Override
        protected ProductoTipoProducto crearInstanciaVacia() {
            ProductoTipoProducto nuevaRelacion = new ProductoTipoProducto();

            nuevaRelacion.setFechaCreacion(OffsetDateTime.now());
            nuevaRelacion.setActivo(true);
            nuevaRelacion.setIdTipoProducto(new TipoProducto());
            if(productoFrm.getRegistro()!=null){
                nuevaRelacion.setIdProducto(productoFrm.getRegistro());
                System.out.println("id del producto asignado a la relacion No vacio "+productoFrm.getRegistro());
                return nuevaRelacion;
            }
            else {
                System.out.println("VACIO");
                return nuevaRelacion;
            }


        }

    @Override
    protected UUID getId(ProductoTipoProducto entidad) {
        return entidad != null ? entidad.getId() : null;
    }

    @Override
    protected String getIdAsText(ProductoTipoProducto r) {
        return r != null && r.getId() != null ? r.getId().toString() : null;
    }

    @Override
    protected ProductoTipoProducto getIdByText(String id) {
        try {
            return dao.findById(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            log(Level.WARNING, "ID inválido: {0}", e);
            return null;
        }
    }

    public boolean isFormularioTipo() {
        return formularioTipo;
    }
    public boolean mostrarFormularioTipo() {
        return formularioTipo;
    }

    public boolean OcultarFormularioTipo() {
        return !formularioTipo;
    }

    public void cerrarDialogoTipo() {
        this.formularioTipo = false;
    }
    @Override
    protected void crear(ProductoTipoProducto entidad) {
        // Asignar las entidades relacionadas si están seleccionadas
        if (productoSeleccionado != null) {
            entidad.setIdProducto(productoSeleccionado);
        }
        if (tipoProductoSeleccionado != null) {
            entidad.setIdTipoProducto(tipoProductoSeleccionado);
        }

        dao.create(entidad);
        limpiarSelecciones();
    }

    @Override
    protected void modificar(ProductoTipoProducto entidad) {
        // Actualizar las entidades relacionadas si están seleccionadas
        if (productoSeleccionado != null) {
            entidad.setIdProducto(productoSeleccionado);
        }
        if (tipoProductoSeleccionado != null) {
            entidad.setIdTipoProducto(tipoProductoSeleccionado);
        }

        dao.update(entidad);
        limpiarSelecciones();
    }

    @Override
    protected void eliminar(ProductoTipoProducto entidad) {
        dao.delete(entidad);
    }

    @Override
    protected ProductoTipoProducto findById(UUID id) {
        return dao.findById(id);
    }

    @Override
    protected List<ProductoTipoProducto> findRange(int first, int pageSize) {
        return dao.findRange(first, pageSize);
    }

        public List<ProductoTipoProducto> findByIdP(UUID idProducto) {
            return dao.findByProducto(idProducto);
        }

    // Métodos específicos para ProductoTipoProducto

    public void seleccionarProducto(Producto producto) {
        this.productoSeleccionado = producto;
        if (this.registro != null) {
            this.registro.setIdProducto(producto);
        }
    }

    public void seleccionarTipoProducto(TipoProducto tipoProducto) {
        this.tipoProductoSeleccionado = tipoProducto;
        if (this.registro != null) {
            this.registro.setIdTipoProducto(tipoProducto);
        }
    }

    public void limpiarProducto() {
        this.productoSeleccionado = null;
        if (this.registro != null) {
            this.registro.setIdProducto(null);
        }
    }

    public void limpiarTipoProducto() {
        this.tipoProductoSeleccionado = null;
        if (this.registro != null) {
            this.registro.setIdTipoProducto(null);
        }
    }

    private void limpiarSelecciones() {
        this.productoSeleccionado = null;
        this.tipoProductoSeleccionado = null;
    }

    // Métodos para buscar relaciones específicas
    public List<ProductoTipoProducto> getRelacionesPorProducto(UUID idProducto) {
        return dao.findByProducto(idProducto);
    }

    public List<ProductoTipoProducto> getRelacionesPorTipoProducto(UUID idTipoProducto) {
        return dao.findByTipoProducto(idTipoProducto);
    }

    public List<ProductoTipoProducto> getRelacionesActivasPorProducto(UUID idProducto) {
        return dao.findActivosByProducto(idProducto);
    }



    // Getters y Setters
    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public TipoProducto getTipoProductoSeleccionado() {
        return tipoProductoSeleccionado;
    }

    public void setTipoProductoSeleccionado(TipoProducto tipoProductoSeleccionado) {
        this.tipoProductoSeleccionado = tipoProductoSeleccionado;
    }

    public ProductoFrm getProductoFrm() {
        return productoFrm;
    }

    public TipoProductoFrm getTipoProductoFrm() {
        return tipoProductoFrm;
    }

    // Métodos auxiliares para la vista
    public String getNombreProducto(ProductoTipoProducto relacion) {
        return relacion != null && relacion.getIdProducto() != null ?
                relacion.getIdProducto().getNombreProducto() : "N/A";
    }

    public String getNombreTipoProducto(ProductoTipoProducto relacion) {
        return relacion != null && relacion.getIdTipoProducto() != null ?
                relacion.getIdTipoProducto().getNombre() : "N/A";
    }}