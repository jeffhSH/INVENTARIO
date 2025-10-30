package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.UnidadMedida;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UnidadMedidaDAOTest {

    List<UnidadMedida> lista;
    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UnidadMedida e = new UnidadMedida();
            e.setId(i);
            lista.add(e);
        }
    }

    @Test
    void create() throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO(mockEm);
        Field emField = UnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        UnidadMedida al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }

    @Test
    void findByTipoUnidadMedida() throws Exception{
        TipoUnidadMedida tipoBuscado = new TipoUnidadMedida();
        tipoBuscado.setId(1); // Integer según tu entidad
        UnidadMedida unidad1 = new UnidadMedida();
        unidad1.setId(1);
        UnidadMedida unidad2 = new UnidadMedida();
        unidad2.setId(2);
        List<UnidadMedida> expectedList = Arrays.asList(unidad1, unidad2);
        EntityManager mockEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO(mockEm);
        Field emField = UnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(mockEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<UnidadMedida> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(UnidadMedida.class)).thenReturn(cq);

        Root<UnidadMedida> root = mock(Root.class);
        when(cq.from(UnidadMedida.class)).thenReturn(root);
        Predicate predicate = mock(Predicate.class);
        when(cb.equal(root.get("idTipoUnidadMedida"), tipoBuscado)).thenReturn(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cq.where(predicate)).thenReturn(cq);
        TypedQuery<UnidadMedida> tq = mock(TypedQuery.class);
        when(mockEm.createQuery(cq)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(expectedList);

        List<UnidadMedida> result = cut.findByTipoUnidadMedida(tipoBuscado);

        assertEquals(expectedList, result);
        verify(mockEm).getCriteriaBuilder();
        verify(cb).createQuery(UnidadMedida.class);
        verify(cq).from(UnidadMedida.class);
        verify(cb).equal(root.get("idTipoUnidadMedida"), tipoBuscado);
        verify(cq).where(predicate);
        verify(mockEm).createQuery(cq);
        verify(tq).getResultList();
    }

    @Test
    void countByActivo()throws Exception{
        boolean activo = true;
        long expectedCount = 5L;

        EntityManager mockEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO(mockEm);
        Field emField = UnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(mockEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Long> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(Long.class)).thenReturn(cq);

        Root<UnidadMedida> root = mock(Root.class);
        when(cq.from(UnidadMedida.class)).thenReturn(root);

        Predicate predicate = mock(Predicate.class);
        when(cb.equal(root.get("activo"), activo)).thenReturn(predicate);
        when(cq.select(cb.count(root))).thenReturn(cq);
        when(cq.where(predicate)).thenReturn(cq);

        TypedQuery<Long> tq = mock(TypedQuery.class);
        when(mockEm.createQuery(cq)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(expectedCount);

        long result = cut.countByActivo(activo);

        assertEquals(expectedCount, result);
        verify(mockEm).getCriteriaBuilder();
        verify(cb).createQuery(Long.class);
        verify(cq).from(UnidadMedida.class);
        verify(cb).equal(root.get("activo"), activo);
        verify(cq).select(cb.count(root));
        verify(cq).where(predicate);
        verify(mockEm).createQuery(cq);
        verify(tq).getSingleResult();
    }

    @Test
    void findByActivo() throws Exception{
        boolean activo = true;
        UnidadMedida ud1 = new UnidadMedida();
        UnidadMedida ud2 = new UnidadMedida();
        List<UnidadMedida> expectedList = Arrays.asList(ud1, ud2);

        EntityManager mockEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO(mockEm);
        Field emField = UnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        when(mockEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<UnidadMedida> cq = mock(CriteriaQuery.class);
        when(cb.createQuery(UnidadMedida.class)).thenReturn(cq);

        Root<UnidadMedida> root = mock(Root.class);
        when(cq.from(UnidadMedida.class)).thenReturn(root);

        Predicate predicate = mock(Predicate.class);
        when(cb.equal(root.get("activo"), activo)).thenReturn(predicate);
        when(cq.select(root)).thenReturn(cq);
        when(cq.where(predicate)).thenReturn(cq);

        TypedQuery<UnidadMedida> tq = mock(TypedQuery.class);
        when(mockEm.createQuery(cq)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(expectedList);

        List<UnidadMedida> result = cut.findByActivo(activo);

        assertEquals(expectedList, result);
        verify(mockEm).getCriteriaBuilder();
        verify(cb).createQuery(UnidadMedida.class);
        verify(cq).from(UnidadMedida.class);
        verify(cb).equal(root.get("activo"), activo);
        verify(cq).select(root);
        verify(cq).where(predicate);
        verify(mockEm).createQuery(cq);
        verify(tq).getResultList();
    }

    @Test
    void buscarPorTipo()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO(mockEm);
        Field emField = UnidadMedidaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.buscarPorTipo(null));
        assertEquals("ID TipoProducto no puede ser nulo", thrown.getMessage());
        List<UnidadMedida> resultadoEsperado = new ArrayList<>();
        UnidadMedida u1 = new UnidadMedida();
        u1.setId(1);
        resultadoEsperado.add(u1);

        TypedQuery<UnidadMedida> mockTq = mock(TypedQuery.class);
        when(mockEm.createQuery("SELECT ud FROM UnidadMedida ud WHERE ud.idTipoUnidadMedida.id=:idTipoU", UnidadMedida.class))
                .thenReturn(mockTq);
        when(mockTq.setParameter("idTipoU", 5)).thenReturn(mockTq); // ID de prueba
        when(mockTq.getResultList()).thenReturn(resultadoEsperado);

        List<UnidadMedida> resultado = cut.buscarPorTipo(5);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());

        verify(mockEm).createQuery(anyString(), eq(UnidadMedida.class));
        verify(mockTq).setParameter("idTipoU", 5);
        verify(mockTq).getResultList();
    }
}
