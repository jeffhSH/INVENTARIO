package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;
import java.util.List;
import java.util.UUID;

@FacesConverter(value = "productoConverter")
public class ProductoConverter implements Converter<Producto> {

    public ProductoConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - productoConverter");
    }

    @Override
    public Producto getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            return null;
        }

        try {
            UUID id = UUID.fromString(value);
            System.out.println("🔍 Buscando producto con ID: " + id);

            // Buscar el managed bean que contiene la lista de productos
            ProductoFrm bean = (ProductoFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{productoFrm}", ProductoFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                System.out.println("✅ productoFrm encontrado");

                // Primero buscar en la lista de productos activos
                List<Producto> items = bean.findProductosActivos();
                System.out.println("📋 Productos en lista activos: " + (items != null ? items.size() : 0));

                if (items != null) {
                    for (Producto item : items) {
                        if (item.getId().equals(id)) {
                            System.out.println("✅ Producto ENCONTRADO en lista: " + item.getNombreProducto());
                            return item;
                        }
                    }
                }

                // Si no se encuentra en la lista activa, buscar por ID
                System.out.println("🔍 Buscando producto por ID en BD...");
                Producto resultado = bean.findById(id);
                if (resultado != null) {
                    System.out.println("✅ Producto ENCONTRADO por findById: " + resultado.getNombreProducto());
                } else {
                    System.out.println("❌ Producto NO ENCONTRADO para ID: " + id);
                }
                return resultado;
            } else {
                System.out.println("❌ productoFrm NO ENCONTRADO");
            }

        } catch (Exception e) {
            System.err.println("❌ Error en ProductoConverter: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Producto value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}