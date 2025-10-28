package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProductoCaracteristica;

import java.io.Serializable;
import java.util.List;

@LocalBean
@Stateless
public class TipoProductoCaracteristicaDAO extends InventarioDefaultDataAccess<TipoProductoCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;
    public TipoProductoCaracteristicaDAO() {
        super(TipoProductoCaracteristica.class);
    }
    public TipoProductoCaracteristicaDAO(EntityManager em) {
        super(TipoProductoCaracteristica.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoProductoCaracteristica> findByTipoProducto(TipoProducto tipoProducto) {
        return em.createQuery(
                        "SELECT tpc FROM TipoProductoCaracteristica tpc WHERE tpc.idTipoProducto = :tipoProducto ORDER BY tpc.idCaracteristica.nombre",
                        TipoProductoCaracteristica.class)
                .setParameter("tipoProducto", tipoProducto)
                .getResultList();
    }

    public List<TipoProductoCaracteristica> findByTipoProductoId(Long idTipoProducto) {
        return em.createQuery(
                        "SELECT tpc FROM TipoProductoCaracteristica tpc WHERE tpc.idTipoProducto.id = :idTipoProducto ORDER BY tpc.idCaracteristica.nombre",
                        TipoProductoCaracteristica.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }


    }

