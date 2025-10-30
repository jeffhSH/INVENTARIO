package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoAlmacen;
import java.util.List;

@FacesConverter(value = "tipoAlmacenConverter")
public class TipoAlmacenConverter implements Converter<TipoAlmacen> {

    public TipoAlmacenConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - tipoAlmacenConverter");
    }

    @Override
    public TipoAlmacen getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            System.out.println("🔍 Valor recibido: " + value);
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);

            // Buscar el managed bean que contiene la lista de tipos de almacén
            TipoAlmacenFrm bean = (TipoAlmacenFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{tipoAlmacenFrm}", TipoAlmacenFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                // Primero buscar en la lista de tipos de almacén del bean
                List<TipoAlmacen> items = bean.getListaTipoAlmacen();
                if (items != null) {
                    for (TipoAlmacen item : items) {
                        if (item.getId() != null && item.getId().equals(id)) {
                            System.out.println("TipoAlmacen ENCONTRADO: " + item.getId());
                            return item;
                        }
                    }
                }

                // Si no se encuentra en la lista, buscar por ID directamente
                TipoAlmacen resultado = bean.findById(id);
                if (resultado != null) {
                    System.out.println("TipoAlmacen ENCONTRADO por findById: " + resultado.getId());
                }
                return resultado;
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ Error en TipoAlmacenConverter - ID no es un número válido: " + value);
        } catch (Exception e) {
            System.err.println("❌ Error en TipoAlmacenConverter: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipoAlmacen value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}