package com.example.myapplication.persistencia

/**
 * Interfaz que define las operaciones básicas de un CRUD.
 * @param T tipo de dato de la entidad a la que pertenecen los datos.
 */
interface DAO<T> {
    /**
     * Inserta un objeto en la base de datos.
     * @param t objeto a insertar.
     * @return verdadero si se ha insertado correctamente, falso en caso contrario.
     */
    fun insertar(t: T): Boolean

    /**
     * Actualiza un objeto en la base de datos.
     * @param t objeto a actualizar.
     * @return verdadero si se ha actualizado correctamente, falso en caso contrario.
     */
    fun actualizar(t: T): Boolean

    /**
     * Busca un objeto en la base de datos.
     * @param t objeto a buscar.
     * @return objeto encontrado, o null si no se ha encontrado.
     */
    fun buscar(t: T): T?

    /**
     * Elimina un objeto de la base de datos.
     * @param t objeto a eliminar.
     * @return verdadero si se ha eliminado correctamente, falso en caso contrario.
     */
    fun eliminar(t: T): Boolean
}