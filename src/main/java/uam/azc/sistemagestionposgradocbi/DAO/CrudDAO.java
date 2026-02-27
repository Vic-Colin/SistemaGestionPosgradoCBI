package uam.azc.sistemagestionposgradocbi.dao;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones básicas
 * CRUD (Create, Read, Update, Delete).
 *
 * Se utiliza como contrato para todos los DAO del sistema,
 * asegurando uniformidad en el acceso a datos.
 *
 * @param <T> Tipo de entidad
 * @param <K> Tipo de clave primaria
 *
 * @author Vania Alejandra Contreras Torres
 */

public interface CrudDAO<T, K> {

    /**
     * Obtiene todos los registros.
     * @return lista de entidades
     */
    List<T> listar();

    /**
     * Busca un registro por su identificador.
     * @param id clave primaria
     * @return entidad encontrada o null
     */
    T buscarPorId(K id);
    
    /**
    * Inserta un nuevo registro en la base de datos.
    *
    * @param objeto entidad a insertar
    * @return true si la operación fue exitosa
    */
    boolean insertar(T objeto);

    /**
    * Actualiza la información de un registro existente.
    *
    * @param objeto entidad con datos actualizados
    * @return true si la actualización fue exitosa
    */
    boolean actualizar(T objeto);

    /**
    * Elimina un registro mediante su identificador.
    *
    * @param id identificador del registro
    * @return true si fue eliminado correctamente
    */
    boolean eliminar(K id);
}

