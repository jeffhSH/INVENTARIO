package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoProductoCaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Named
@ViewScoped
public class TipoProductoCaracteristicaFrm extends DefaultFrm<TipoProductoCaracteristica, Long> implements Serializable {

    @Inject
    private TipoProductoCaracteristicaDAO dao;
    @Inject
    private  TipoProductoFrm tipoProductoFrm;
    @Inject
    private TipoProductoDAO tipoProductoDAO;
    @PostConstruct
    @Override
    protected void initDefaultFrm() {
        super.initDefaultFrm();
        if (getRegistro() == null) {
            setRegistro(crearInstanciaVacia());
        }
    }
    @Override
    public TipoProductoCaracteristica getRegistro() {
        if (super.getRegistro() == null) {
            setRegistro(crearInstanciaVacia());
        }
        return super.getRegistro();
    }

    @Override
    protected void inicializar() {
    }

    @Override
    protected InventarioDefaultDataAccess<TipoProductoCaracteristica> getDao() {
        return dao;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Gestión de Características de Tipo Producto";
    }

    @Override
    protected TipoProductoCaracteristica crearInstanciaVacia() {

        TipoProductoCaracteristica nuevaRelacion = new TipoProductoCaracteristica();
        nuevaRelacion.setIdCaracteristica(new Caracteristica());
        nuevaRelacion.setFechaCreacion(OffsetDateTime.now());


        if (tipoProductoFrm.getRegistro() != null) {
            TipoProducto tipoPersistido = tipoProductoDAO.findById(tipoProductoFrm.getRegistro().getId());
            nuevaRelacion.setIdTipoProducto(tipoPersistido);
            System.out.println("✅ Tipo asignado en crearInstanciaVacia: " + tipoPersistido.getNombre());

        }
        return nuevaRelacion;
    }
    public Long getTipoProductoId() {
        return registro != null && registro.getIdTipoProducto() != null
                ? registro.getIdTipoProducto().getId() : null;
    }

    public void setTipoProductoId(Long id) {
        if (registro != null && id != null) {
            TipoProducto tp = new TipoProducto();
            tp.setId(id);
            registro.setIdTipoProducto(tp);
        }
    }

    public Integer getCaracteristicaId() {
        return registro != null && registro.getIdCaracteristica() != null
                ? registro.getIdCaracteristica().getId() : null;
    }

    public void setCaracteristicaId(Integer id) {
        if (registro != null && id != null) {
            Caracteristica car = new Caracteristica();
            car.setId(id);
            registro.setIdCaracteristica(car);
        }
    }
    public OffsetDateTime getCurrentTime() {
        return OffsetDateTime.now();
    }

    @Override
    protected Long getId(TipoProductoCaracteristica entidad) {
        return entidad != null ? entidad.getId() : null;
    }

    @Override
    protected String getIdAsText(TipoProductoCaracteristica r) {
        return r != null && r.getId() != null ? r.getId().toString() : null;
    }

    @Override
    protected TipoProductoCaracteristica getIdByText(String id) {
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
    protected void crear(TipoProductoCaracteristica entidad) {
        dao.create(entidad);
    }

    @Override
    protected void modificar(TipoProductoCaracteristica entidad) {
        dao.update(entidad);
    }

    @Override
    protected void eliminar(TipoProductoCaracteristica entidad) {
        dao.delete(entidad);
    }

    @Override
    public TipoProductoCaracteristica findById(Long id) {
        return dao.findById(id);
    }

    @Override
    protected List<TipoProductoCaracteristica> findRange(int first, int pageSize) {
        return dao.findRange(first, pageSize);
    }

    public List<TipoProductoCaracteristica> findByTipoProductoId(Long idTipoProducto) {
        return dao.findByTipoProductoId(idTipoProducto);
    }
}