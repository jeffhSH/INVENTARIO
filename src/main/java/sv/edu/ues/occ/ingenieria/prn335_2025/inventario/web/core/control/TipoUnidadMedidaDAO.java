package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;

import java.io.Serializable;

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


    public Integer obtenerProximoId() {
        try {
            EntityManager em = getEntityManager();

            if (em != null) {
                // Obtener el máximo ID actual de la tabla
                Query maxQuery = em.createQuery(
                        "SELECT MAX(u.id) FROM TipoUnidadMedida u"
                );
                Integer maxId = (Integer) maxQuery.getSingleResult();
                System.out.println("DEBUG: Max ID actual = " + maxId);

                if (maxId != null && maxId > 0) {
                    return maxId + 1;
                }
            }
            return 1;
        } catch (Exception e) {
            System.out.println("DEBUG: Error obteniendo próximo ID: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }
}


