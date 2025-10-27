package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;
import java.util.List;

@FacesConverter(value = "tipoProductoConverter")
public class TipoProductoConverter implements Converter<TipoProducto> {
    public TipoProductoConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - tipoProductoConverter");
    }

    @Override
    public TipoProducto getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            System.out.println("🔍 Valor recibido: " + value);
            return null;

        }

        try {
            Long id = Long.parseLong(value);

            TipoProductoFrm bean = (TipoProductoFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{tipoProductoFrm}", TipoProductoFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                List<TipoProducto> items = bean.getTiposPadre(); // O el método que uses para obtener la lista
                if (items != null) {
                    for (TipoProducto item : items) {
                        if (item.getId().equals(id)) {
                            System.out.println("TipoProducto ENCONTRADO: " + item.getNombre());
                            return item;
                        }
                    }
                }
                TipoProducto resultado = bean.findById(id);
                return resultado;
            }

        } catch (Exception e) {
            System.err.println("Error en TipoProductoConverter: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipoProducto value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}