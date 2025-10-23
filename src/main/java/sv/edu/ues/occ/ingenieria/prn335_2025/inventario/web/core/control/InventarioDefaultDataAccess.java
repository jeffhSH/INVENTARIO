package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class InventarioDefaultDataAccess<T> implements InventarioDAOInterface<T>, Serializable {
    protected final Class<T> entityClass;

    public InventarioDefaultDataAccess(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    // Helpers
    private EntityManager requireEm() {
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("EntityManager no inicializado");
        }
        return em;
    }

    protected void validarEntidad(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entidad no puede ser nula");
        }
    }

    // Implementaciones default
    @Override
    public void create(T entity) {
        validarEntidad(entity);
        EntityManager em =requireEm();
        em.persist(entity);

    }

    @Override
    public T update(T entity) {
        validarEntidad(entity);
        EntityManager em = requireEm();
        return em.merge(entity);
    }

    @Override
    public void delete(T entity) {
        validarEntidad(entity);
        EntityManager em = requireEm();
        if (em.contains(entity)) {
            em.remove(entity);
        } else {
            em.remove(em.merge(entity));
        }
    }

    @Override
    public T findById(Object id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");
        EntityManager em = requireEm();
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findRange(int first, int max) {
        if (first < 0 || max < 1) throw new IllegalArgumentException("Parámetros inválidos: first >= 0, max >= 1");

        EntityManager em = requireEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root).orderBy(cb.asc(root.get("id")));

        TypedQuery<T> query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @Override
    public int count() {
        EntityManager em = requireEm();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root));
        return em.createQuery(cq).getSingleResult().intValue();
    }
}
