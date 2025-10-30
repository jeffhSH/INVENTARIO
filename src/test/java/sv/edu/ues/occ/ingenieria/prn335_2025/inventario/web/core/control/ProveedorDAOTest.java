package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Proveedor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProveedorDAOTest {

    List<Proveedor> lista;
    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Proveedor e = new Proveedor();
            e.setId(i);
            lista.add(e);
        }
    }

    @Test
    void create() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProveedorDAO cut = new ProveedorDAO();
        Field emField = ProveedorDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Proveedor al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }

    @Test
    void ObtenerProxId() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        Query mockQuery = Mockito.mock(Query.class);

        when(mockEm.createQuery("SELECT MAX(p.id) FROM Proveedor p")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(10);

        ProveedorDAO cut = new ProveedorDAO();
        Field emField = ProveedorDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Integer result = cut.obtenerProximoId();

        assertEquals(11, result);
        verify(mockEm).createQuery("SELECT MAX(p.id) FROM Proveedor p");
        verify(mockQuery).getSingleResult();
    }
    @Test
    void obtenerProximoId_nullMax() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        Query mockQuery = Mockito.mock(Query.class);

        when(mockEm.createQuery("SELECT MAX(p.id) FROM Proveedor p")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(null);

        ProveedorDAO cut = new ProveedorDAO();
        Field emField = ProveedorDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Integer result = cut.obtenerProximoId();

        assertEquals(1, result);
    }

    @Test
    void obtenerProximoId_excepcion() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        when(mockEm.createQuery(anyString())).thenThrow(new RuntimeException("DB error"));

        ProveedorDAO cut = new ProveedorDAO();
        Field emField = ProveedorDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Integer result = cut.obtenerProximoId();

        assertEquals(1, result);
    }

}
