package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Compra;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Stateless
@LocalBean
public class CompraDAO extends InventarioDefaultDataAccess<Compra>{
    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public CompraDAO() {
        super(Compra.class);
    }

    @Override
    public int count() {
        return super.count();
    }
    public CompraDAO(EntityManager em) {
        super(Compra.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Compra> findByEstado(String estado) {
        return em.createQuery("SELECT c FROM Compra c WHERE c.estado = :estado", Compra.class)
                .setParameter("estado", estado)
                .getResultList();
    }
    @Override
    public List<Compra> findRange(int first, int max) {
        return em.createQuery(
                        "SELECT c FROM Compra c ORDER BY c.id", Compra.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }
}
