    package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

    import java.util.List;

    public interface InventarioDAOInterface<T> {


        void create(T entity);

        T update(T entity);

        void delete(T entity);

        T findById(Object id);

        public List<T> findRange(int first, int max) throws IllegalArgumentException;

        public int count() throws IllegalArgumentException;
    }
