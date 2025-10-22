package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.AlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Almacen;

import java.io.Serializable;

public class AlmacenFrm extends DefaultFrm<Almacen, Integer> implements Serializable {
    @Inject
    FacesContext facesContext;
    @Inject
    AlmacenDAO almacenDAO;
}
