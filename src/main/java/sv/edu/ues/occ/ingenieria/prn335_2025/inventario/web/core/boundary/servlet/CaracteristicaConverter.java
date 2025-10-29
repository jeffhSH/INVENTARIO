package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Caracteristica;

import java.util.List;
@FacesConverter(value = "caracteristicaConverter")
public class CaracteristicaConverter implements Converter<Caracteristica> {
    public CaracteristicaConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - caracteristicaConverter");
    }


    @Override
    public Caracteristica getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try {
            Integer id = Integer.parseInt(value);
            CaracteristicaFrm bean = context.getApplication().evaluateExpressionGet(context, "#{caracteristicaFrm}", CaracteristicaFrm.class);

            // Busca directamente por ID en lugar de en la lista
            return bean.findById(id);

        } catch (Exception e) {
            System.err.println("Error en converter: " + e.getMessage());
            return null;
        }
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Caracteristica value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}

