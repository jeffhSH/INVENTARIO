package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Proveedor;
import java.io.Serializable;
@Stateless
@LocalBean
public class ProveedorDAO extends InventarioDefaultDataAccess<Proveedor> implements Serializable {
    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;
    public ProveedorDAO(Class<Proveedor> entityClass) {super(entityClass);}
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    public ProveedorDAO() { super(Proveedor.class); }
    public Integer obtenerProximoId() {
        try {

            EntityManager em = getEntityManager();

            if (em != null) {
                // Obtener el máximo ID actual de la tabla
                Query maxQuery = em.createQuery(
                        "SELECT MAX(p.id) FROM Proveedor p"
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


