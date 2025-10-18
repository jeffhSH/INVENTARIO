package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ClienteDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.control.ProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Cliente;
import sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity.Producto;

@Named
@ViewScoped
public class ClienteFrm extends DefaultFrm<Cliente, UUID> implements Serializable {


    @Inject
    ClienteDAO ClienteDAO;

    private List<Cliente> ListaCliente;


    @Override
    protected Object getDao() {
        return ClienteDAO;
    }

    @Override
    protected String getNombreBeanConfig() {
        return "Clientes";
    }

    @Override
    protected void inicializar() {
        try {
            ListaCliente= ClienteDAO.findRange(0, Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", e.getMessage())
            );
        }
    }



    // ===== Implementaciones CRUD =====
    @Override
    protected Cliente crearInstanciaVacia() {
        return new Cliente();
    }
    @Override
    protected String getIdAsText(Cliente c) {
        if (c != null && c.getId() != null) {
            return c.getId().toString();
        }
        return null;
    }

    @Override
    protected Cliente getIdByText(String id) {
        if (id != null && this.model != null && !this.model.getWrappedData().isEmpty()) {
            try {
                UUID buscado = UUID.fromString(id);
                return this.model.getWrappedData().stream()
                        .filter(r -> r.getId() != null && r.getId().equals(buscado))
                        .findFirst()
                        .orElse(null);
            } catch (NumberFormatException e) {
                System.err.println("ID no es un número válido: " + id);
                return null;
            }
        }
        return null;
    }

    @Override
    protected UUID getId(Cliente entidad) {
        return entidad.getId();
    }

    @Override
    protected void crear(Cliente entidad) {
        ClienteDAO.create(entidad);
    }

    @Override
    protected void modificar(Cliente entidad) {
        ClienteDAO.update(entidad);
    }

    @Override
    protected void eliminar(Cliente entidad) {
        ClienteDAO.delete(entidad);
    }

    @Override
    protected Cliente findById(UUID id) {
        return ClienteDAO.findById(id);
    }

    @Override
    protected List<Cliente> findRange(int first, int pageSize) {
        return ClienteDAO.findRange(first, pageSize);
    }

    // ===== GETTERS / SETTERS ESPECÍFICOS =====
    public List<Cliente> getListaCliente() {
        return ListaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.ListaCliente = ListaCliente;
    }


}
