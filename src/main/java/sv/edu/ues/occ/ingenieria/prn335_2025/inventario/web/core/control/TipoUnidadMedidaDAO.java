package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoUnidadMedidaDAO extends InventarioDefaultDataAccess<TipoUnidadMedida> implements Serializable {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public TipoUnidadMedidaDAO() {
        super(TipoUnidadMedida.class);
    }

    // Constructor para testing (Mockito)
    public TipoUnidadMedidaDAO(EntityManager em) {
        super(TipoUnidadMedida.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

   public List<TipoUnidadMedida>getlistaActivos(){

       return em.createQuery(
                       "SELECT t FROM TipoUnidadMedida t WHERE t.activo =true ORDER BY t.nombre",
                       TipoUnidadMedida.class)
               .getResultList();


   }
}


