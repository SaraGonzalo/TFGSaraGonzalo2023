package com.example.myapplication.utilidades

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

/**
 * Clase utilizada para administrar la base de datos en la aplicación.
 * Se encarga de abrir y cerrar la conexión con la base de datos, así como proporcionar
 * acceso al objeto SQLiteDatabase para realizar operaciones de base de datos.
 *
 * @param context El contexto de la aplicación.
 */
class DBManager(context: Context?) {
    private val dbConexion: DBConexion // Objeto para gestionar la conexión con la base de datos
    private var sqLiteDatabase: SQLiteDatabase? = null // Objeto para realizar operaciones en la base de datos

    /**
     * Constructor de la clase DBManager.
     * Inicializa el objeto DBConexion.
     *
     * @param context El contexto de la aplicación.
     */
    init {
        dbConexion = DBConexion(context) // Inicializa la instancia de DBConexion
    }
    /**
     * Retorna la instancia de SQLiteDatabase utilizada para realizar operaciones en la base de datos.
     *
     * @return La instancia de SQLiteDatabase o null si no se ha inicializado.
     */
    fun getSqLiteDatabase(): SQLiteDatabase? {
        return sqLiteDatabase
    }
    /**
     * Abre la conexión con la base de datos y devuelve una instancia de DBManager.
     *
     * @return La instancia actual de DBManager.
     * @throws SQLException Si ocurre algún error al abrir la base de datos.
     */
    @Throws(SQLException::class)
    fun abrir(): DBManager {
        sqLiteDatabase = dbConexion.writableDatabase // Abre la conexión con la base de datos en modo escritura
        return this // Devuelve una instancia de DBManager con la conexión abierta
    }
    /**
     * Cierra la conexión con la base de datos.
     * Es importante llamar a esta función cuando ya no se necesite acceder a la base de datos.
     * @return No devuelve ningún valor.
     */
    fun cerrar() {
        dbConexion.close() // Cierra la conexión con la base de datos
    }
    /**
     * Compañero de objeto que contiene constantes relacionadas con la tabla de la base de datos.
     * Es como si fuera un objeto estático dentro de una clase
     */
    companion object {
        // Constantes para los nombres de la tabla y los campos
        private const val NOMBRE_TABLA = "usuarios"
        private const val CAMPO1 = "nombre"
        private const val CAMPO2 = "contrasena"
        private const val CAMPO3 = "puntuacionCalculo"
        private const val CAMPO4 = "puntuacionIngles"
        private const val CAMPO5 = "puntuacionBanderas"
        private const val CAMPO6 = "puntuacionMemoria"
        private const val CAMPO7 = "vidas"
        // Sentencia SQL para crear la tabla en la base de datos
        private const val CREAR_TABLA =
            "create table " + NOMBRE_TABLA + "(" + CAMPO1 + " text primary key," + CAMPO2 + " text not null," +
                    CAMPO3 + " integer," + CAMPO4 + " integer," + CAMPO5 + " integer," + CAMPO6 + " integer," + CAMPO7 + " integer)"
        /**
         * Obtiene el nombre de la tabla en la base de datos.
         *
         * @return El nombre de la tabla.
         */
        val nombreTabla: String
            get() = NOMBRE_TABLA
        /**
         * Obtiene la sentencia SQL para crear la tabla en la base de datos.
         *
         * @return La sentencia SQL para crear la tabla.
         */
        val crearTabla: String
            get() = CREAR_TABLA
        /**
         * Nombre del campo 1 en la tabla de la base de datos.
         * @return nombre del primer campo de la tabla
         */
        val campo1: String
            get() = CAMPO1
        /**
         * Nombre del campo 2 en la tabla de la base de datos.
         * @return nombre del segundo campo de la tabla
         */
        val campo2: String
            get() = CAMPO2
        /**
         * Nombre del campo 3 en la tabla de la base de datos.
         * @return nombre del tercer campo de la tabla
         */
        val campo3: String
            get() = CAMPO3
        /**
         * Nombre del campo 4 en la tabla de la base de datos.
         * @return nombre del cuarto campo de la tabla
         */
        val campo4: String
            get() = CAMPO4
        /**
         * Nombre del campo 5 en la tabla de la base de datos.
         * @return nombre del quinto campo de la tabla
         */
        val campo5: String
            get() = CAMPO5
        /**
         * Nombre del campo 6 en la tabla de la base de datos.
         * @return nombre del sexto campo de la tabla
         */
        val campo6: String
            get() = CAMPO6
        /**
         * Nombre del campo 7 en la tabla de la base de datos.
         * @return nombre del séptimo campo de la tabla
         */
        val campo7: String
            get() = CAMPO7
    }
}