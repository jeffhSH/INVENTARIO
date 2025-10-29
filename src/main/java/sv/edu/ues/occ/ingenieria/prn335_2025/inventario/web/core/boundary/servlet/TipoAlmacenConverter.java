package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;

@FacesConverter("tipoAlmacenConverter")
public class TipoAlmacenConverter implements Converter<TipoAlmacen> {

    @Inject
    private TipoAlmacenDAO tipoAlmacenDAO;

    @Override
    public TipoAlmacen getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        Integer id = Integer.valueOf(s.split(" - ")[0].trim());
        return tipoAlmacenDAO.findById(id);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, TipoAlmacen tipoAlmacen) {
        if (tipoAlmacen == null || tipoAlmacen.getId() == null) {
            return "";
        }
        return tipoAlmacen.getId() + " - " + tipoAlmacen.getNombre();
    }
}
