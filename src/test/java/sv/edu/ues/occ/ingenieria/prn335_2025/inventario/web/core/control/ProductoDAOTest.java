package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ProductoDAOTest {

    List<Producto> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Producto e = new Producto();
            e.setId(UUID.randomUUID());
            lista.add(e);
        }
    }

    @Test
    void crear() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoDAO cut = new ProductoDAO();

        Field emField = ProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Producto p = lista.get(0);
        cut.create(p);
        verify(mockEm).persist(p);
        //El UUID es nulo
        ProductoDAO cot = new ProductoDAO();
        emField.set(cot, mockEm);
        p.setId(null);
        cut.create(p);
        assertNotNull(p.getId(), "Se debe generar un UUID si el ID es nulo");
        //Caso de registro nulo
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.create(null));
        assertEquals("El registro no puede ser nulo", thrown.getMessage());
        //Caso de Manager Nulo
        emField.set(cut, null);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.create(p));
        assertEquals("Error al crear el registro", thrown2.getMessage());
    }
}
