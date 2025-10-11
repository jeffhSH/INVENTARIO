@Named
@ViewScoped
public class TipoAlmacenFrm extends DefaultFrm<TipoAlmacen, Integer> {

    @Inject
    TipoAlmacenDAO dao;

    @Override protected String getNombreBean() { return "Tipo de Almacén"; }

    @Override protected TipoAlmacen crearInstanciaVacia() { return new TipoAlmacen(); }

    @Override protected Integer getId(TipoAlmacen e) { return e.getId(); }

    @Override protected void crear(TipoAlmacen e) { dao.create(e); }

    @Override protected void modificar(TipoAlmacen e) { dao.update(e); }

    @Override protected void eliminar(TipoAlmacen e) { dao.delete(e); }

    @Override protected TipoAlmacen findById(Integer id) { return dao.findById(id); }

    @Override protected List<TipoAlmacen> findRange(int first, int pageSize) {
        return dao.findRange(first, pageSize);
    }

    @Override protected int count() { return dao.count(); }
}
