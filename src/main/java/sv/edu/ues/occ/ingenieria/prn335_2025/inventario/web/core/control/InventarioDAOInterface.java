package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import java.util.List;

public interface InventarioDAOInterface<T> {
    public void crear(T registro) throws IllegalArgumentException, IllegalAccessException;

    /*
    public void leer(T registro) throws IllegalArgumentException, IllegalAccessException;
    */
    /*
     * @param id Identificador de la entidad a eliminar
     * @throws IllegalArgumentException Si la entidad es nula
     * @throws IllegalStateException

    public void eliminar(Object id)  throws IllegalArgumentException, IllegalStateException;
     */

    //public T buscarPorId(Object id) throws IllegalArgumentException;

    // ✅ IMPLEMENTACIONES DEFAULT PARA TODOS LOS DAOs
    void create(T entity);

    T update(T entity);

    void delete(T entity);

    T findById(Object id);

    public List<T> findRange(int first, int max) throws IllegalArgumentException;

    public int count() throws IllegalArgumentException;
}
