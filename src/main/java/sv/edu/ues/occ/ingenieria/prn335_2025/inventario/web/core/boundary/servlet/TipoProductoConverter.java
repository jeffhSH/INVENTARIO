package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoProducto;

import java.util.List;

@FacesConverter("tipoProductoConverter")
public class TipoProductoConverter implements Converter<TipoProducto> {

    @Override
    public TipoProducto getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("=== CONVERTER getAsObject ===");
        System.out.println("Value: " + value);

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            Long id = Long.parseLong(value);

            // Buscar en los items del selectOneMenu
            List<TipoProducto> items = (List<TipoProducto>) component.getAttributes().get("items");
            if (items != null) {
                for (TipoProducto item : items) {
                    if (item.getId().equals(id)) {
                        System.out.println("Encontrado en lista: " + item.getNombre());
                        return item;
                    }
                }
            }

            System.out.println("No encontrado en lista, creando instancia vacía");
            // Si no está en la lista, crear una instancia básica
            TipoProducto tipo = new TipoProducto();
            tipo.setId(id);
            return tipo;

        } catch (Exception e) {
            System.err.println("Error en converter: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipoProducto value) {
        System.out.println("=== CONVERTER getAsString ===");
        System.out.println("Value: " + value);

        if (value == null) {
            return "";
        }

        if (value.getId() != null) {
            String idStr = value.getId().toString();
            System.out.println("Retornando ID: " + idStr);
            return idStr;
        } else {
            System.out.println("ID es nulo, retornando cadena vacía");
            return "";
        }
    }
}