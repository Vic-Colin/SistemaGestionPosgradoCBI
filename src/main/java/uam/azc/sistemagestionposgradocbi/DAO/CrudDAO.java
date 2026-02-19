package uam.azc.sistemagestionposgradocbi.dao;

import java.util.List;

public interface CrudDAO<T, K> {

    List<T> listar();

    T buscarPorId(K id);

    boolean insertar(T objeto);

    boolean actualizar(T objeto);

    boolean eliminar(K id);
}

