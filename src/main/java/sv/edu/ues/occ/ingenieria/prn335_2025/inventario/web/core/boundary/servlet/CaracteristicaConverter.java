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

        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            System.out.println("🔍 Valor recibido: " + value);
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);

            CaracteristicaFrm bean = (CaracteristicaFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{caracteristicaFrm}", CaracteristicaFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                List<Caracteristica> items = bean.getListaCompleta();
                if (items != null) {
                    for (Caracteristica item : items) {
                        if (item.getId().equals(id)) {
                            System.out.println("Caracteristica ENCONTRADA: " + item.getNombre());
                            return item;
                        }
                    }
                }
                Caracteristica resultado = bean.findById(id);
                return resultado;
            }

        } catch (Exception e) {
            System.err.println("Error en CaracteristicaConverter: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Caracteristica value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}

