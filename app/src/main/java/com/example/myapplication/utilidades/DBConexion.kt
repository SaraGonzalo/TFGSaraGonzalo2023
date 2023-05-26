package com.example.myapplication.utilidades

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
/**
 * Clase utilizada para administrar la conexión y la creación de la base de datos.
 *
 * @param context El contexto de la aplicación.
 */
//crea una instancia de la clase DBConexion, que extiende la clase SQLiteOpenHelper y toma como parámetros: context, NOMBRE_DB,null,VERSION_DB
//hereda funcionalidad de SQLiteOpenHelper y establece los parámetros necesarios para la creación y administración de la base de datos en SQLite.
class DBConexion(context: Context?) :
    SQLiteOpenHelper(context, NOMBRE_DB, null, VERSION_DB) {
    /**
     * Método invocado al crear la base de datos por primera vez.
     * Ejecuta la sentencia SQL para crear la tabla en la base de datos.
     *
     * @param sqLiteDatabase La instancia de SQLiteDatabase para ejecutar la sentencia SQL.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        // Ejecuta la sentencia SQL para crear la tabla en la base de datos
        sqLiteDatabase.execSQL(DBManager.crearTabla)
    }
    /**
     * Método invocado al actualizar la versión de la base de datos.
     * Elimina la tabla existente y la vuelve a crear.
     *
     * @param sqLiteDatabase La instancia de SQLiteDatabase para ejecutar la sentencia SQL.
     * @param oldVersion La versión anterior de la base de datos.
     * @param newVersion La nueva versión de la base de datos.
     * @return No devuelve ningún valor.
     */
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBManager.nombreTabla) //eliminar la tabla especificada de la base de datos
    }

    companion object {
        /**
         * Nombre de la base de datos.
         */
        private const val NOMBRE_DB = "personas.db"

        /**
         * Versión de la base de datos.
         */
        private const val VERSION_DB = 1
    }
}