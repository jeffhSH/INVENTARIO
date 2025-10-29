package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClienteDAOTest {

    List<Cliente> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Cliente e = new Cliente();
            e.setId(UUID.randomUUID());
            lista.add(e);
        }
    }

    @Test
    void crear() throws Exception {
        EntityManager mockedEm = mock(EntityManager.class);
        ClienteDAO cut = new ClienteDAO(mockedEm);
        Field emField = ClienteDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockedEm);
        Cliente cl = lista.get(0);
        cut.create(cl);
        verify(mockedEm).persist(cl);
        //El UUID es nulo
        ClienteDAO cot = new ClienteDAO();
        emField.set(cot, mockedEm);
        cl.setId(null);
        cut.create(cl);
        assertNotNull(cl.getId(), "Se debe generar un UUID si el ID es nulo");
        //Caso de registro nulo
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.create(null));
        assertEquals("El registro no puede ser nulo", thrown.getMessage());
        //Caso de Manager Nulo
        emField.set(cut, null);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.create(cl));
        assertEquals("Error al crear el registro", thrown2.getMessage());

    }
}
