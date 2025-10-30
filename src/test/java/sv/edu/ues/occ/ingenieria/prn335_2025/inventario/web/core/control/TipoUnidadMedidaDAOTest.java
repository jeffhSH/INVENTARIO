package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TipoUnidadMedidaDAOTest {

    List<TipoUnidadMedida> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TipoUnidadMedida e = new TipoUnidadMedida();
            e.setId(i);
            lista.add(e);
        }
    }

    @Test
    void create() throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TipoUnidadMedidaDAO cut = new TipoUnidadMedidaDAO(mockEm);
        Field emField = TipoUnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        TipoUnidadMedida al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }

    @Test
    void GetList() throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoUnidadMedida> mockQuery = mock(TypedQuery.class);
        TipoUnidadMedida tipo1 = new TipoUnidadMedida();
        tipo1.setNombre("Peso");

        List<TipoUnidadMedida> listaEsperada = Arrays.asList(tipo1);

        when(mockEm.createQuery(anyString(), eq(TipoUnidadMedida.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(listaEsperada);

        TipoUnidadMedidaDAO dao = new TipoUnidadMedidaDAO();
        Field emField = TipoUnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(dao, mockEm);

        List<TipoUnidadMedida> resultado = dao.getlistaActivos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Peso", resultado.get(0).getNombre());
        verify(mockEm).createQuery(contains("SELECT t FROM TipoUnidadMedida t"), eq(TipoUnidadMedida.class));
        verify(mockQuery).getResultList();
    }
}
