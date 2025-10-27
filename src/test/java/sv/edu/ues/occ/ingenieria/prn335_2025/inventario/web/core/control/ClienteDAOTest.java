package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify;

public class ClienteDAOTest {

    private List<Cliente> listaCliente;

    @BeforeEach
    public void setUp() throws Exception {
        listaCliente = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Cliente e = new Cliente();
            e.setNombre("Cliente " + i);
            listaCliente.add(e);
        }
    }

    @Test
    void crearCliente() throws IllegalAccessException {
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        Cliente cl = listaCliente.get(0);
        ClienteDAO cut = new ClienteDAO(mockedEm);
        cut.create(cl);
        verify(mockedEm).persist(cl);
    }

}
