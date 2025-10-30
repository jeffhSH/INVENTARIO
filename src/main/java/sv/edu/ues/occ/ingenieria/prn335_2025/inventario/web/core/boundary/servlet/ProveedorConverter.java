package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Proveedor;
import java.util.List;

@FacesConverter(value = "proveedorConverter")
public class ProveedorConverter implements Converter<Proveedor> {

    public ProveedorConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - proveedorConverter");
    }

    @Override
    public Proveedor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            System.out.println("🔍 Valor recibido: " + value);
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);

            // Buscar el managed bean que contiene la lista de proveedores
            ProveedorFrm bean = (ProveedorFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{proveedorFrm}", ProveedorFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                // Primero buscar en la lista de proveedores activos
                List<Proveedor> items = bean.findActivos();
                if (items != null) {
                    for (Proveedor item : items) {
                        if (item.getId().equals(id)) {
                            System.out.println("Proveedor ENCONTRADO: " + item.getNombre());
                            return item;
                        }
                    }
                }

                // Si no se encuentra en la lista activa, buscar por ID
                Proveedor resultado = bean.findById(id);
                if (resultado != null) {
                    System.out.println("Proveedor ENCONTRADO por findById: " + resultado.getNombre());
                }
                return resultado;
            }

        } catch (Exception e) {
            System.err.println("❌ Error en ProveedorConverter: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Proveedor value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}