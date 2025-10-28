package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.util.List;


@Stateless
@LocalBean
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
    public  List<Caracteristica> getListaCompleta(){
        return em.createQuery(
                        "SELECT c FROM Caracteristica c ORDER BY c.nombre",
                        Caracteristica.class)
                .getResultList();


    }
}

