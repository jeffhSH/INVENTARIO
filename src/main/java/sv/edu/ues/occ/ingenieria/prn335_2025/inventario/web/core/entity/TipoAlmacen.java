package sv.edu.ues.occ.ingenieria.prn335_2025.inventario.web.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tipo_almacen", schema = "public")
public class TipoAlmacen {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoAlmacen)) return false;
        TipoAlmacen other = (TipoAlmacen) o;
        // Igualdad por ID (cuando ya fue asignado)
        return this.id != null && this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "TipoAlmacen{id=" + id + ", nombre=" + nombre + "}";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_almacen", nullable = false)
    private Integer id;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "obsevaciones")
    private String observaciones;

    public TipoAlmacen() {
        this.activo = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String obsevaciones) {
        this.observaciones = obsevaciones;
    }

}