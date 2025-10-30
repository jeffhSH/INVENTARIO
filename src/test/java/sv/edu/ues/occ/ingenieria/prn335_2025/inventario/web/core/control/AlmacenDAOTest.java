package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Almacen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class AlmacenDAOTest {

    private List<Almacen> almacenList;

    @BeforeEach
    public void setUp() throws Exception {
        almacenList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Almacen e = new Almacen();
            e.setId(i);
            e.setObservaciones("Observaciones " + i);
            almacenList.add(e);
        }
    }

    @Test
    void crear()throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        AlmacenDAO cut = new AlmacenDAO();
        Field emField = CaracteristicaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Almacen al = almacenList.get(0);

        cut.create(al);

        verify(mockEm).persist(al);
    }

    @Test
    void obtenerTipoAlmacen() {
        // Preparar mocks
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        var mockCb = Mockito.mock(jakarta.persistence.criteria.CriteriaBuilder.class);
        var mockCq = Mockito.mock(jakarta.persistence.criteria.CriteriaQuery.class);
        var mockRoot = Mockito.mock(jakarta.persistence.criteria.Root.class);
        var mockQuery = Mockito.mock(jakarta.persistence.TypedQuery.class);

        AlmacenDAO cut = new AlmacenDAO();
        cut.em = mockEm;

        int idTipo = 1;
        List<Almacen> resultadosEsperados = almacenList; // usa la lista creada en setUp()

        // Configurar comportamiento simulado
        Mockito.when(mockEm.getCriteriaBuilder()).thenReturn(mockCb);
        Mockito.when(mockCb.createQuery(Almacen.class)).thenReturn(mockCq);
        Mockito.when(mockCq.from(Almacen.class)).thenReturn(mockRoot);
        Mockito.when(mockEm.createQuery(mockCq)).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(resultadosEsperados);

        // Ejecutar método a probar
        List<Almacen> resultados = cut.buscarTipo(idTipo);

        // Verificar interacciones y resultados
        Mockito.verify(mockEm).createQuery(mockCq);
        Mockito.verify(mockQuery).getResultList();

        assertEquals(resultadosEsperados.size(), resultados.size());
    }
}
