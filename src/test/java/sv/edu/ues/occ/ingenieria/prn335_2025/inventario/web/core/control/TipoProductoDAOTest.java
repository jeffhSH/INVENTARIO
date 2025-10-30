package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

public class TipoProductoDAOTest {

    List<TipoProducto> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TipoProducto e = new TipoProducto();
            e.setId(Long.valueOf(i));
            lista.add(e);
        }
    }

    @Test
    void create() throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        TipoProducto al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }

    @Test
    void FindByNombre()throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findByNombre(null));
        assertEquals("El nombre no puede ser nulo o vacío", thrown.getMessage());
        // Mock de TypedQuery
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        List<TipoProducto> listaEsperada = Arrays.asList(new TipoProducto(), new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("nombre"), anyString())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(listaEsperada);

        // Ejecución
        List<TipoProducto> resultado = cut.findByNombre("Lácteos");

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockEm).createQuery(contains("FROM TipoProducto"), eq(TipoProducto.class));
        verify(mockQuery).setParameter(eq("nombre"), eq("%Lácteos%"));
        verify(mockQuery).getResultList();
    }

    @Test
    void FindActivos() throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        List<TipoProducto> listaEsperada = Arrays.asList(new TipoProducto(), new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(listaEsperada);

        List<TipoProducto> resultado = cut.findActivos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockEm).createQuery(contains("t.activo = true"), eq(TipoProducto.class));
        verify(mockQuery).getResultList();
    }

    @Test
    void FindTiposPadres()throws Exception {
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        List<TipoProducto> listaEsperada = Arrays.asList(new TipoProducto(), new TipoProducto(), new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(listaEsperada);

        List<TipoProducto> resultado = cut.findTiposPadre();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(mockEm).createQuery(contains("t.idTipoProductoPadre IS NULL"), eq(TipoProducto.class));
        verify(mockQuery).getResultList();
    }

    @Test
    void FindSubTipo()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findSubtipos(null));
        assertEquals("El tipo padre no puede ser nulo", thrown.getMessage());
        TipoProducto tipoPadre = new TipoProducto();

        List<TipoProducto> subtiposEsperados = Arrays.asList(new TipoProducto(), new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("padre"), eq(tipoPadre))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(subtiposEsperados);
        List<TipoProducto> resultado = cut.findSubtipos(tipoPadre);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockEm).createQuery(contains("t.idTipoProductoPadre = :padre"), eq(TipoProducto.class));
        verify(mockQuery).setParameter("padre", tipoPadre);
        verify(mockQuery).getResultList();
    }

    @Test
    void FindSubTiposById()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findSubtiposById(null));
        assertEquals("El ID del tipo padre no puede ser nulo", thrown.getMessage());
        Long idPadre = 1L;

        List<TipoProducto> subtiposEsperados = Arrays.asList(new TipoProducto(), new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("idPadre"), eq(idPadre))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(subtiposEsperados);

        List<TipoProducto> resultado = cut.findSubtiposById(idPadre);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockEm).createQuery(contains("t.idTipoProductoPadre.id = :idPadre"), eq(TipoProducto.class));
        verify(mockQuery).setParameter("idPadre", idPadre);
        verify(mockQuery).getResultList();
    }

    @Test
    void ExisteNombre()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.existeNombre(null));
        assertEquals("El nombre no puede ser nulo o vacío", thrown.getMessage());
        String nombre = "ProductoTest";

        TypedQuery<Long> mock = mock(TypedQuery.class);
        when(mockEm.createQuery(anyString(), eq(Long.class))).thenReturn(mock);
        when(mock.setParameter(eq("nombre"), eq(nombre))).thenReturn(mock);
        when(mock.getSingleResult()).thenReturn(1L); // Simula que existe

        boolean existe = cut.existeNombre(nombre);

        assertTrue(existe);

        when(mock.getSingleResult()).thenReturn(0L);
        boolean noExiste = cut.existeNombre(nombre);
        assertFalse(noExiste);

        verify(mockEm, times(2)).createQuery(contains("SELECT COUNT(t)"), eq(Long.class));
        verify(mock, times(2)).setParameter("nombre", nombre);
        verify(mock, times(2)).getSingleResult();
    }

    @Test
    void existeNombreExcluyendoId() throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.existeNombreExcluyendoId(null, Long.valueOf(1)));
        assertEquals("El nombre no puede ser nulo o vacío", thrown.getMessage());
        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> cut.existeNombreExcluyendoId("null", null));
        assertEquals("El ID a excluir no puede ser nulo", thrown2.getMessage());

        TypedQuery<Long> mockQuer = mock(TypedQuery.class);

        String nombre = "ProductoTest";
        Long idExcluir = 5L;

        when(mockEm.createQuery(anyString(), eq(Long.class))).thenReturn(mockQuer);
        when(mockQuer.setParameter("nombre", nombre)).thenReturn(mockQuer);
        when(mockQuer.setParameter("idExcluir", idExcluir)).thenReturn(mockQuer);

        when(mockQuer.getSingleResult()).thenReturn(1L);
        boolean existe = cut.existeNombreExcluyendoId(nombre, idExcluir);
        assertTrue(existe);

        when(mockQuer.getSingleResult()).thenReturn(0L);
        boolean noExiste = cut.existeNombreExcluyendoId(nombre, idExcluir);
        assertFalse(noExiste);

        verify(mockEm, times(2)).createQuery(contains("SELECT COUNT(t)"), eq(Long.class));
        verify(mockQuer, times(2)).setParameter("nombre", nombre);
        verify(mockQuer, times(2)).setParameter("idExcluir", idExcluir);
        verify(mockQuer, times(2)).getSingleResult();
    }

    @Test
    void FindAll()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        List<TipoProducto> expectedList = Arrays.asList(
                new TipoProducto(), new TipoProducto()
        );

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<TipoProducto> result = cut.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedList, result);

        verify(mockEm).createQuery(contains("SELECT t FROM TipoProducto t"), eq(TipoProducto.class));
        verify(mockQuery).getResultList();
    }

    @Test
    void contarSubTipos()throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.contarSubtipos(null));
        assertEquals("El tipo padre no puede ser nulo", thrown.getMessage());
        TipoProducto tipoPadre = new TipoProducto();
        TypedQuery<Long> mockQuery = mock(TypedQuery.class);

        when(mockEm.createQuery(anyString(), eq(Long.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("padre"), eq(tipoPadre))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(5L);

        long result = cut.contarSubtipos(tipoPadre);

        assertEquals(5L, result);

        verify(mockEm).createQuery(contains("SELECT COUNT(t) FROM TipoProducto t"), eq(Long.class));
        verify(mockQuery).setParameter("padre", tipoPadre);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void FindByEstado() throws Exception{
        EntityManager mockEm = mock(EntityManager.class);
        TypedQuery<TipoProducto> mockQuery = mock(TypedQuery.class);

        TipoProductoDAO cut = new TipoProductoDAO();
        Field emField = TipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        List<TipoProducto> listaMock = new ArrayList<>();
        listaMock.add(new TipoProducto());

        when(mockEm.createQuery(anyString(), eq(TipoProducto.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("activo"), eq(true))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(listaMock);

        List<TipoProducto> result = cut.findByEstado(true);

        assertEquals(listaMock, result);
        verify(mockEm).createQuery(contains("SELECT t FROM TipoProducto t WHERE t.activo = :activo"), eq(TipoProducto.class));
        verify(mockQuery).setParameter("activo", true);
        verify(mockQuery).getResultList();
    }
    @Test
    void testFindByEstadoActivoNull() throws Exception {
        TipoProductoDAO cut = spy(new TipoProductoDAO());

        doReturn(new ArrayList<>()).when(cut).findAll();

        List<TipoProducto> result = cut.findByEstado(null);

        assertNotNull(result);
        verify(cut).findAll();
    }
}
