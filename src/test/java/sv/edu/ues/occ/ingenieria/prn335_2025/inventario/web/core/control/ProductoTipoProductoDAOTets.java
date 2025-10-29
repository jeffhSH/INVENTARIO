package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.ProductoTipoProducto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoTipoProductoDAOTets {

    List<ProductoTipoProducto> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProductoTipoProducto e = new ProductoTipoProducto();
            e.setId(UUID.randomUUID());
            lista.add(e);
        }
    }
    @Test
    void create() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();
        Field emField = CaracteristicaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        ProductoTipoProducto al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }

    @Test
    void FindByProductoUUID() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findByProducto(null));
        assertEquals("ID Producto no puede ser nulo", thrown.getMessage());

        // Crear datos de prueba
        UUID idProducto = UUID.randomUUID();
        ProductoTipoProducto ptp1 = new ProductoTipoProducto();
        ProductoTipoProducto ptp2 = new ProductoTipoProducto();
        List<ProductoTipoProducto> expectedList = List.of(ptp1, ptp2);

        // Mockear TypedQuery
        TypedQuery<ProductoTipoProducto> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto",
                ProductoTipoProducto.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<ProductoTipoProducto> actualList = cut.findByProducto(idProducto);

        // Verificaciones
        assertEquals(expectedList, actualList);
        verify(mockEm).createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto",
                ProductoTipoProducto.class);
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).getResultList();
    }

    @Test
    void FidndByProductoProduct() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findByProduct(null));
        assertEquals("ID Producto no puede ser nulo", thrown.getMessage());

        UUID idProducto = UUID.randomUUID();
        ProductoTipoProducto ptp1 = new ProductoTipoProducto();
        ProductoTipoProducto ptp2 = new ProductoTipoProducto();
        List<ProductoTipoProducto> expectedList = List.of(ptp1, ptp2);

        // Mockear Query
        Query mockQuery = Mockito.mock(Query.class);
        when(mockEm.createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id =:idProducto"))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<ProductoTipoProducto> actualList = cut.findByProduct(idProducto);

        // Verificaciones
        assertEquals(expectedList, actualList);
        verify(mockEm).createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id =:idProducto");
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).getResultList();
    }

    @Test
    void FindByTipoProducto()throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findByTipoProducto(null));
        assertEquals("ID Tipo Producto no puede ser nulo", thrown.getMessage());

        UUID idTipoProducto = UUID.randomUUID();
        ProductoTipoProducto ptp1 = new ProductoTipoProducto();
        ProductoTipoProducto ptp2 = new ProductoTipoProducto();
        List<ProductoTipoProducto> expectedList = List.of(ptp1, ptp2);

        // Mockear TypedQuery
        TypedQuery<ProductoTipoProducto> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto",
                ProductoTipoProducto.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idTipoProducto", idTipoProducto)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<ProductoTipoProducto> actualList = cut.findByTipoProducto(idTipoProducto);

        // Verificaciones
        assertEquals(expectedList, actualList);
        verify(mockEm).createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto",
                ProductoTipoProducto.class);
        verify(mockQuery).setParameter("idTipoProducto", idTipoProducto);
        verify(mockQuery).getResultList();
    }

    @Test
    void FindByActivos()throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findActivosByProducto(null));
        assertEquals("ID Producto no puede ser nulo", thrown.getMessage());

        UUID idProducto = UUID.randomUUID();
        ProductoTipoProducto ptp1 = new ProductoTipoProducto();
        ProductoTipoProducto ptp2 = new ProductoTipoProducto();
        List<ProductoTipoProducto> expectedList = List.of(ptp1, ptp2);

        // Mockear TypedQuery
        TypedQuery<ProductoTipoProducto> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                ProductoTipoProducto.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<ProductoTipoProducto> actualList = cut.findActivosByProducto(idProducto);

        // Verificaciones
        assertEquals(expectedList, actualList);
        verify(mockEm).createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                ProductoTipoProducto.class);
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).getResultList();
    }

    @Test
    void findAvtivoByTipoProducto() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.findActivosByTipoProducto(null));
        assertEquals("ID Tipo Producto no puede ser nulo", thrown.getMessage());

        UUID idTipoProducto = UUID.randomUUID();
        ProductoTipoProducto ptp1 = new ProductoTipoProducto();
        ProductoTipoProducto ptp2 = new ProductoTipoProducto();
        List<ProductoTipoProducto> expectedList = List.of(ptp1, ptp2);

        // Mockear TypedQuery
        TypedQuery<ProductoTipoProducto> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                ProductoTipoProducto.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idTipoProducto", idTipoProducto)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);
        List<ProductoTipoProducto> actualList = cut.findActivosByTipoProducto(idTipoProducto);

        // Verificaciones
        assertEquals(expectedList, actualList);
        verify(mockEm).createQuery(
                "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                ProductoTipoProducto.class);
        verify(mockQuery).setParameter("idTipoProducto", idTipoProducto);
        verify(mockQuery).getResultList();
    }

    @Test
    void Exitrelacion() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.existsRelation(null, null));
        assertEquals("IDs no pueden ser nulos", thrown.getMessage());

        UUID idProducto = UUID.randomUUID();
        UUID idTipoProducto = UUID.randomUUID();

        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.idTipoProducto.id = :idTipoProducto",
                Long.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idTipoProducto", idTipoProducto)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(3L); // Simula que hay registros

        boolean result = cut.existsRelation(idProducto, idTipoProducto);

        assertTrue(result);
        verify(mockEm).createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.idTipoProducto.id = :idTipoProducto",
                Long.class);
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).setParameter("idTipoProducto", idTipoProducto);
        verify(mockQuery).getSingleResult();
    }

    @Test
    void DeactivateByProducto() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.deactivateByProducto(null));
        assertEquals("ID Producto no puede ser nulo", thrown.getMessage());

        UUID idProducto = UUID.randomUUID();
        Query mockQuery = Mockito.mock(Query.class);
        when(mockEm.createQuery(
                "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idProducto.id = :idProducto"))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(5); // Simula que 5 registros se desactivaron

        int result = cut.deactivateByProducto(idProducto);

        assertEquals(5, result);
        verify(mockEm).createQuery(
                "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idProducto.id = :idProducto");
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).executeUpdate();
    }

    @Test
    void DeactivateByTipoProducto() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.deactivateByTipoProducto(null));
        assertEquals("ID Tipo Producto no puede ser nulo", thrown.getMessage());
        UUID idTipoProducto = UUID.randomUUID();

        Query mockQuery = Mockito.mock(Query.class);
        when(mockEm.createQuery(
                "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idTipoProducto.id = :idTipoProducto"))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("idTipoProducto", idTipoProducto)).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(3); // Simula que 3 registros se desactivaron

        int result = cut.deactivateByTipoProducto(idTipoProducto);

        assertEquals(3, result);
        verify(mockEm).createQuery(
                "UPDATE ProductoTipoProducto ptp SET ptp.activo = false WHERE ptp.idTipoProducto.id = :idTipoProducto");
        verify(mockQuery).setParameter("idTipoProducto", idTipoProducto);
        verify(mockQuery).executeUpdate();
    }

    @Test
    void countByProducto() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.countActivosByProducto(null));
        assertEquals("ID Producto no puede ser nulo", thrown.getMessage());
        UUID idProducto = UUID.randomUUID();
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                Long.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idProducto", idProducto)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(5L); // Simula 5 relaciones activas

        long result = cut.countActivosByProducto(idProducto);

        assertEquals(5L, result);
        verify(mockEm).createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto AND ptp.activo = true",
                Long.class);
        verify(mockQuery).setParameter("idProducto", idProducto);
        verify(mockQuery).getSingleResult();
    }
    @Test
    void CountActivosByTipoProducto() throws Exception{
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();

        Field emField = ProductoTipoProductoDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cut.countActivosByTipoProducto(null));
        assertEquals("ID Tipo Producto no puede ser nulo", thrown.getMessage());
        UUID idTipoProducto = UUID.randomUUID();
        TypedQuery<Long> mockQuery = Mockito.mock(TypedQuery.class);
        when(mockEm.createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                Long.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("idTipoProducto", idTipoProducto)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(7L); // Simula 7 relaciones activas

        long result = cut.countActivosByTipoProducto(idTipoProducto);

        assertEquals(7L, result);
        verify(mockEm).createQuery(
                "SELECT COUNT(ptp) FROM ProductoTipoProducto ptp WHERE ptp.idTipoProducto.id = :idTipoProducto AND ptp.activo = true",
                Long.class);
        verify(mockQuery).setParameter("idTipoProducto", idTipoProducto);
        verify(mockQuery).getSingleResult();
    }
}
