package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;



public class CaracteristicaDAO extends InventarioDefaultDataAccess<Caracteristica>{
    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;
    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    public CaracteristicaDAO() {super(Caracteristica.class);}
    public CaracteristicaDAO(EntityManager em) {
        super(Caracteristica.class);
        this.em = em;
    }
}

