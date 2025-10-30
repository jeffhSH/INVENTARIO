package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;
import java.util.List;
import java.util.UUID;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter<Cliente> {

    public ClienteConverter() {
        System.out.println("✅ CONVERTER INSTANCIADO - clienteConverter");
    }

    @Override
    public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️  VALOR NULL O VACÍO DETECTADO en getAsObject()");
            System.out.println("🔍 Valor recibido: " + value);
            return null;
        }

        try {
            UUID id = UUID.fromString(value);

            // Buscar el managed bean que contiene la lista de clientes
            ClienteFrm bean = (ClienteFrm) context.getApplication()
                    .getExpressionFactory()
                    .createValueExpression(context.getELContext(), "#{clienteFrm}", ClienteFrm.class)
                    .getValue(context.getELContext());

            if (bean != null) {
                // Primero buscar en la lista de clientes activos
                List<Cliente> items = bean.findActivos();
                if (items != null) {
                    for (Cliente item : items) {
                        if (item.getId().equals(id)) {
                            System.out.println("Cliente ENCONTRADO: " + item.getNombre());
                            return item;
                        }
                    }
                }

                // Si no se encuentra en la lista activa, buscar por ID
                Cliente resultado = bean.findById(id);
                if (resultado != null) {
                    System.out.println("Cliente ENCONTRADO por findById: " + resultado.getNombre());
                }
                return resultado;
            }

        } catch (Exception e) {
            System.err.println("❌ Error en ClienteConverter: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cliente value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}