package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Venta;

import java.io.Serializable;
import java.util.UUID;
@Stateless
@LocalBean
public class VentaDAO extends InventarioDefaultDataAccess<Venta> implements Serializable {
    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;
    UUID id;
    public VentaDAO() {
        super(Venta.class);
    }


    public VentaDAO(EntityManager em) {
        super(Venta.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Venta registro) throws IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        try {
            EntityManager em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no disponible");
            }
            // Generar UUID si no existe
            if (registro.getId() == null) {
                this.id=UUID.randomUUID();
                registro.setId(id);

            }

            em.persist(registro);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear el registro", ex);
        }

    }
}
