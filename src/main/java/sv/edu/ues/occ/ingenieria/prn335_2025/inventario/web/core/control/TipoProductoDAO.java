package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.util.List;

@Stateless
public class TipoProductoDAO extends InventarioDefaultDataAccess<TipoProducto> {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public TipoProductoDAO() {
        super(TipoProducto.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // Métodos específicos para TipoProducto

    /**
     * Busca tipos de producto por nombre (búsqueda parcial case-insensitive)
     */
    public List<TipoProducto> findByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE LOWER(t.nombre) LIKE LOWER(:nombre) ORDER BY t.nombre",
                        TipoProducto.class)
                .setParameter("nombre", "%" + nombre.trim() + "%")
                .getResultList();
    }

    /**
     * Obtiene todos los tipos de producto activos
     */
    public List<TipoProducto> findActivos() {
        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.activo = true ORDER BY t.nombre",
                        TipoProducto.class)
                .getResultList();
    }

    /**
     * Obtiene los tipos de producto que no tienen padre (categorías principales)
     */
    public List<TipoProducto> findTiposPadre() {
        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.idTipoProductoPadre IS NULL AND t.activo = true ORDER BY t.nombre",
                        TipoProducto.class)
                .getResultList();
    }

    /**
     * Obtiene los subtipos de un tipo de producto padre
     */
    public List<TipoProducto> findSubtipos(TipoProducto tipoPadre) {
        if (tipoPadre == null) {
            throw new IllegalArgumentException("El tipo padre no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.idTipoProductoPadre = :padre ORDER BY t.nombre",
                        TipoProducto.class)
                .setParameter("padre", tipoPadre)
                .getResultList();
    }

    /**
     * Obtiene los subtipos por ID del tipo padre
     */
    public List<TipoProducto> findSubtiposById(Long idTipoPadre) {
        if (idTipoPadre == null) {
            throw new IllegalArgumentException("El ID del tipo padre no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.idTipoProductoPadre.id = :idPadre ORDER BY t.nombre",
                        TipoProducto.class)
                .setParameter("idPadre", idTipoPadre)
                .getResultList();
    }

    /**
     * Verifica si existe un tipo de producto con el mismo nombre
     */
    public boolean existeNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        Long count = em.createQuery(
                        "SELECT COUNT(t) FROM TipoProducto t WHERE LOWER(t.nombre) = LOWER(:nombre)",
                        Long.class)
                .setParameter("nombre", nombre.trim())
                .getSingleResult();
        return count > 0;
    }

    /**
     * Verifica si existe un tipo de producto con el mismo nombre excluyendo un ID específico
     */
    public boolean existeNombreExcluyendoId(String nombre, Long idExcluir) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (idExcluir == null) {
            throw new IllegalArgumentException("El ID a excluir no puede ser nulo");
        }

        Long count = em.createQuery(
                        "SELECT COUNT(t) FROM TipoProducto t WHERE LOWER(t.nombre) = LOWER(:nombre) AND t.id != :idExcluir",
                        Long.class)
                .setParameter("nombre", nombre.trim())
                .setParameter("idExcluir", idExcluir)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Obtiene todos los tipos de producto ordenados por nombre
     */
    public List<TipoProducto> findAll() {
        return em.createQuery(
                        "SELECT t FROM TipoProducto t ORDER BY t.nombre",
                        TipoProducto.class)
                .getResultList();
    }

    /**
     * Cuenta la cantidad de subtipos que tiene un tipo de producto
     */
    public long contarSubtipos(TipoProducto tipoPadre) {
        if (tipoPadre == null) {
            throw new IllegalArgumentException("El tipo padre no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT COUNT(t) FROM TipoProducto t WHERE t.idTipoProductoPadre = :padre",
                        Long.class)
                .setParameter("padre", tipoPadre)
                .getSingleResult();
    }

    /**
     * Obtiene tipos de producto por estado
     */
    public List<TipoProducto> findByEstado(Boolean activo) {
        if (activo == null) {
            return findAll();
        }

        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.activo = :activo ORDER BY t.nombre",
                        TipoProducto.class)
                .setParameter("activo", activo)
                .getResultList();
    }
}