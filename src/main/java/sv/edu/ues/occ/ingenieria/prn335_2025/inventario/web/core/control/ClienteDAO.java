package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;

import java.io.Serializable;
import java.util.UUID;

@Stateless
@LocalBean
public class ClienteDAO extends InventarioDefaultDataAccess<Cliente> implements Serializable {

    @PersistenceContext(unitName = "inventarioPU")
    private EntityManager em;

    public ClienteDAO() {
        super(Cliente.class);
    }

    // Constructor para testing (Mockito)
    public ClienteDAO(EntityManager em) {
        super(Cliente.class);
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    @Override
    public void create(Cliente registro) throws IllegalArgumentException {
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
                registro.setId(UUID.randomUUID());
            }

            em.persist(registro);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al crear el registro", ex);
        }
    }
}


