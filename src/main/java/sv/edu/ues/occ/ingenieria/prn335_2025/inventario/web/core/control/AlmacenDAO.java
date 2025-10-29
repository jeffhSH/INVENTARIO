package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Almacen;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AlmacenDAO extends InventarioDefaultDataAccess<Almacen> implements Serializable {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;


    public AlmacenDAO(){
        super(Almacen.class);
    }
    public  AlmacenDAO(EntityManager em){
        super(Almacen.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Almacen> buscarTipo(int idTipo){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Almacen> cq = cb.createQuery(Almacen.class);
        Root<Almacen> root = cq.from(Almacen.class);

        cq.where(cb.equal(root.get("idTipoAlmacen"), idTipo));
        return null;
    }
}