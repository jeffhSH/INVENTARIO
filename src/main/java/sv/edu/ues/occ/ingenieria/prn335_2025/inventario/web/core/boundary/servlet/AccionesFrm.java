package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.boundary.servlet;

import java.util.function.Consumer;

public class AccionesFrm<T> {
    private Consumer<T> nuevoHandler;
    private Consumer<T> editarHandler;
    private Consumer<T> eliminarHandler;
    private Runnable volverHandler;

    public Consumer<T> getNuevoHandler() {
        return nuevoHandler;
    }

    public void setNuevoHandler(Consumer<T> nuevoHandler) {
        this.nuevoHandler = nuevoHandler;
    }

    public Consumer<T> getEditarHandler() {
        return editarHandler;
    }

    public void setEditarHandler(Consumer<T> editarHandler) {
        this.editarHandler = editarHandler;
    }

    public Consumer<T> getEliminarHandler() {
        return eliminarHandler;
    }

    public void setEliminarHandler(Consumer<T> eliminarHandler) {
        this.eliminarHandler = eliminarHandler;
    }

    public Runnable getVolverHandler() {
        return volverHandler;
    }

    public void setVolverHandler(Runnable volverHandler) {
        this.volverHandler = volverHandler;
    }
}
