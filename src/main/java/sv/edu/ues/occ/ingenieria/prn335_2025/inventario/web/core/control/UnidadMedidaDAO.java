package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;

import java.io.Serializable;
import java.util.List;
@Stateless
@LocalBean
public class UnidadMedidaDAO extends InventarioDefaultDataAccess<UnidadMedida> implements Serializable {
    @PersistenceContext(unitName="inventarioPU")
    private EntityManager em;

    public UnidadMedidaDAO() {super(UnidadMedida.class);}

    public UnidadMedidaDAO(EntityManager em) {
        super(UnidadMedida.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.em;
    }

    public List<UnidadMedida> findByActivo(boolean activo) {
        EntityManager em=getEntityManager();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<UnidadMedida> cq=cb.createQuery(UnidadMedida.class);
        Root<UnidadMedida> root=cq.from(UnidadMedida.class);

        cq.select(root);
        cq.where(cb.equal(root.get("activo"), activo));

        return em.createQuery(cq).getResultList();
    }
    public List<UnidadMedida> findByTipoUnidadMedida(TipoUnidadMedida tipoBuscado) {
        EntityManager em=getEntityManager();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery <UnidadMedida> cq=cb.createQuery(UnidadMedida.class);
        Root<UnidadMedida> root=cq.from(UnidadMedida.class);

        cq.select(root);
        cq.where(cb.equal(root.get("idTipoUnidadMedida"), tipoBuscado));
        return em.createQuery(cq).getResultList();
    }
    public long countByActivo(boolean activo) {
        em=getEntityManager();
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Long> cq=cb.createQuery(Long.class);
        Root<UnidadMedida> root=cq.from(UnidadMedida.class);

        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("activo"), activo));
        return em.createQuery(cq).getSingleResult();
    }

    public List<UnidadMedida> buscarPorTipo(Integer idTipoU){
        if (idTipoU == null) {
            throw new IllegalArgumentException("ID TipoProducto no puede ser nulo");
        }
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT ud FROM UnidadMedida ud WHERE ud.idTipoUnidadMedida.id=:idTipoU", UnidadMedida.class)
                .setParameter("idTipoU",idTipoU).getResultList();
    }

}
