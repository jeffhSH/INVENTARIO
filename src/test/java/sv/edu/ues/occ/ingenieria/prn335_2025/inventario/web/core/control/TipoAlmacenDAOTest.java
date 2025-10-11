package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TipoAlmacenDAOTest {

    protected List<TipoAlmacen> findResult;

    @BeforeEach
    void setUp() {
        findResult = Arrays.asList(
                new TipoAlmacen(),
                new TipoAlmacen(),
                new TipoAlmacen()
        );

        // Configurar IDs para las entidades de prueba
        for (int i = 0; i < findResult.size(); i++) {
            findResult.get(i).setId(i + 1);
        }
    }


    @Test
    void create() {
        System.out.println("TipoAlmacenDAOTest.create");
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        TipoAlmacen nuevo = new TipoAlmacen();
        nuevo.setId(1);

        // ❌ ELIMINAR esta línea:
        // TipoAlmacenDAO cut = new TipoAlmacenDAO(); // em == null

        // ✅ AGREGAR esta línea:
        TipoAlmacenDAO cut = new TipoAlmacenDAO(mockEM);

        // 1) Entidad nula → IllegalArgumentException (PRIMERO ahora)
        assertThrows(IllegalArgumentException.class, () -> {
            cut.create(null);
        });

        // 2) Caso feliz: creación exitosa
        cut.create(nuevo);
        verify(mockEM).persist(nuevo);

        // ❌ ELIMINAR esta sección completa:
        // // 2) em == null → IllegalStateException
        // assertThrows(IllegalStateException.class, () -> {
        //     cut.create(nuevo);
        // });
        // // 3) Caso feliz: se inyecta el EntityManager
        // cut.em = mockEM;
        // cut.create(nuevo);
    }
    @Test
    void findById() {
        System.out.println("TipoAlmacenDAOTest.findById");

        final Integer idEsperado = 1;
        TipoAlmacen esperado = new TipoAlmacen();
        esperado.setId(idEsperado);

        // ✅ CORREGIDO: Crear el mock y asignarlo al DAO
        EntityManager mock = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO(mock);

        // Simula la búsqueda en la base de datos
        Mockito.when(mock.find(TipoAlmacen.class, idEsperado)).thenReturn(esperado);

        // ✅ EJECUTAR la prueba
        TipoAlmacen resultado = cut.findById(idEsperado);

        // ✅ VERIFICAR resultados
        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        assertEquals(idEsperado, resultado.getId());

        // ✅ Test si se lanza excepción para id nulo
        assertThrows(IllegalArgumentException.class, () -> {
            cut.findById(null);
        });
    }

    @Test
    void findRange() {
        System.out.println("TipoAlmacenDAOTest.findRange");

        int first = 0;
        int max = 1000;

        // ✅ CORREGIDO: Configurar mocks correctamente
        EntityManager mock = Mockito.mock(EntityManager.class);
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<TipoAlmacen> cqMock = Mockito.mock(CriteriaQuery.class);
        Root<TipoAlmacen> rootMock = Mockito.mock(Root.class);
        TypedQuery<TipoAlmacen> tqMock = Mockito.mock(TypedQuery.class);

        // Configurar cadena de mocks
        Mockito.when(mock.getCriteriaBuilder()).thenReturn(cbMock);
        Mockito.when(cbMock.createQuery(TipoAlmacen.class)).thenReturn(cqMock);
        Mockito.when(cqMock.from(TipoAlmacen.class)).thenReturn(rootMock);
        Mockito.when(cqMock.select(rootMock)).thenReturn(cqMock);
        Mockito.when(mock.createQuery(cqMock)).thenReturn(tqMock);
        Mockito.when(tqMock.setFirstResult(first)).thenReturn(tqMock);
        Mockito.when(tqMock.setMaxResults(max)).thenReturn(tqMock);
        Mockito.when(tqMock.getResultList()).thenReturn(findResult);

        TipoAlmacenDAO cut = new TipoAlmacenDAO(mock);

        List<TipoAlmacen> encontrados = cut.findRange(first, max);

        assertNotNull(encontrados);
        assertEquals(findResult.size(), encontrados.size());

        // ✅ VERIFICAR llamadas
        verify(tqMock).setFirstResult(first);
        verify(tqMock).setMaxResults(max);
        verify(tqMock).getResultList();
    }


    @Test
    void delete() {
        System.out.println("TipoAlmacenDAOTest.delete");

        // ✅ AGREGAR esta línea al inicio:
        EntityManager emMock = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO(emMock);

        TipoAlmacen eliminado = new TipoAlmacen();
        eliminado.setId(1);

        // Test if exception is thrown for null entity
        assertThrows(IllegalArgumentException.class, () -> {
            cut.delete(null);
        });

        // ❌ ELIMINAR estas líneas:
        // EntityManager emMock = Mockito.mock(EntityManager.class);

        // Simula que la entidad existe en el contexto
        Mockito.when(emMock.contains(eliminado)).thenReturn(true);
        cut.delete(eliminado);
        Mockito.verify(emMock, Mockito.times(1)).remove(eliminado);

        // ❌ ELIMINAR esta línea:
        // cut.em = emMock;
    }


    @Test
    void update() {
        System.out.println("TipoAlmacenDAOTest.update");

        // ✅ AGREGAR esta línea al inicio:
        EntityManager emMock = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO(emMock);

        TipoAlmacen modificado = new TipoAlmacen();
        modificado.setId(1); // ✅ CAMBIAR de UUID a Integer

        // Test if exception is thrown for null entity
        assertThrows(IllegalArgumentException.class, () -> {
            cut.update(null);
        });


        Mockito.when(emMock.merge(modificado)).thenReturn(modificado);

        TipoAlmacen resultado = cut.update(modificado);
        assertNotNull(resultado);
        assertEquals(modificado, resultado);
    }

    @Test
    void count() {
        System.out.println("TipoAlmacenDAOTest.count");

        // Crear el DAO con EntityManager mock
        EntityManager emMock = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO(emMock);

        // Configurar los mocks
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Long> cqMock = Mockito.mock(CriteriaQuery.class);
        Root<TipoAlmacen> rootMock = Mockito.mock(Root.class);
        Expression<Long> countExpressionMock = Mockito.mock(Expression.class);
        TypedQuery<Long> tqMock = Mockito.mock(TypedQuery.class);

        // Configurar la cadena de llamadas
        Mockito.when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        Mockito.when(cbMock.createQuery(Long.class)).thenReturn(cqMock);
        Mockito.when(cqMock.from(TipoAlmacen.class)).thenReturn(rootMock);
        Mockito.when(cbMock.count(rootMock)).thenReturn(countExpressionMock);
        Mockito.when(cqMock.select(countExpressionMock)).thenReturn(cqMock);
        Mockito.when(emMock.createQuery(cqMock)).thenReturn(tqMock);
        Mockito.when(tqMock.getSingleResult()).thenReturn(3L);

        // Ejecutar el método
        int resultado = cut.count();

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(3, resultado);

        // Verificar que se llamaron los métodos esperados
        verify(emMock).getCriteriaBuilder();
        verify(cbMock).createQuery(Long.class);
        verify(cqMock).from(TipoAlmacen.class);
        verify(cbMock).count(rootMock);
        verify(emMock).createQuery(cqMock);
        verify(tqMock).getSingleResult();
    }
}