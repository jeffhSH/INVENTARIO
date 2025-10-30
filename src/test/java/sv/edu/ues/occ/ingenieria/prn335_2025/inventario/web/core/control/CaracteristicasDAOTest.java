package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify;

public class CaracteristicasDAOTest {

    List<Caracteristica> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Caracteristica e = new Caracteristica();
            e.setId(i);
            lista.add(e);
        }
    }

    @Test
    void create() throws Exception {
        EntityManager mockEm = Mockito.mock(EntityManager.class);
        CaracteristicaDAO cut = new CaracteristicaDAO();
        Field emField = CaracteristicaDAO.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(cut, mockEm);

        Caracteristica al = lista.get(0);
        cut.create(al);
        verify(mockEm).persist(al);
    }
}
