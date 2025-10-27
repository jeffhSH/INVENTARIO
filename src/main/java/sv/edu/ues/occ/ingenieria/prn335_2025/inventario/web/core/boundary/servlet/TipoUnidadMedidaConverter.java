            package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

            import jakarta.faces.component.UIComponent;
            import jakarta.faces.context.FacesContext;
            import jakarta.faces.convert.Converter;
            import jakarta.faces.convert.FacesConverter;
            import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.TipoUnidadMedida;
            import java.util.List;

            @FacesConverter(value = "tipoUnidadMedidaConverter")
            public class TipoUnidadMedidaConverter implements Converter<TipoUnidadMedida> {

                @Override
                public TipoUnidadMedida getAsObject(FacesContext context, UIComponent component, String value) {

                    if (value == null || value.trim().isEmpty()) {
                        return null;
                    }
                    try {
                        Integer id = Integer.parseInt(value);

                        TipoUnidadMedidaFrm bean = (TipoUnidadMedidaFrm) context.getApplication()
                                .getExpressionFactory()
                                .createValueExpression(context.getELContext(), "#{tipoUnidadMedidaFrm}", TipoUnidadMedidaFrm.class)
                                .getValue(context.getELContext());

                        if (bean != null) {
                            List<TipoUnidadMedida> items = bean.getlistaAtivos();
                            if (items != null) {
                                for (TipoUnidadMedida item : items) {
                                    if (item.getId().equals(id)) {
                                        System.out.println("ENCONTRADO: " + item.getNombre());
                                        return item;
                                    }
                                 }
                            }
                            TipoUnidadMedida resultado = bean.findById(id);
                            return resultado;
                        }

                    } catch (Exception e) {
                        System.err.println("Error en converter: " + e.getMessage());
                    }
                    return null;
                }

                @Override
                public String getAsString(FacesContext context, UIComponent component, TipoUnidadMedida value) {
                    if (value == null) {
                        return "";
                    }
                    return value.getId() != null ? value.getId().toString() : "";
                }
            }