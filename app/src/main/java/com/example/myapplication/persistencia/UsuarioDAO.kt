package com.example.myapplication.persistencia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.widget.Toast
import com.example.myapplication.utilidades.DBManager
import com.example.myapplication.modelodatos.Usuario

/**
 * Clase que implementa el DAO para la entidad Usuario.
 *
 * @param context El contexto de la aplicación.
 */
class UsuarioDAO(private val context: Context) : DAO<Usuario> {
    // Instancia de DBManager para administrar la conexión y las operaciones con la base de datos
    //context: referencia al estado actual de la aplicación y proporciona acceso a diversos recursos y servicios del sistema.
    private val dbManager: DBManager = DBManager(context)
    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a insertar.
     * @return true si se ha insertado correctamente, false en caso contrario.
     */
    override fun insertar(usuario: Usuario): Boolean {
        var insertado: Long = -1
        try {
            // Abrir la conexión con la base de datos
            dbManager.abrir()
            // Crear un objeto ContentValues para almacenar los valores a insertar
            val contentValues = ContentValues()
            // Asignar los valores del objeto Usuario a ContentValues
            contentValues.put(DBManager.campo1, usuario.getNombre())
            contentValues.put(DBManager.campo2, usuario.getContrasena())
            contentValues.put(DBManager.campo3, usuario.getPuntuacionCalculo())
            contentValues.put(DBManager.campo4, usuario.getPuntuacionIngles())
            contentValues.put(DBManager.campo5, usuario.getPuntuacionBandera())
            contentValues.put(DBManager.campo6, usuario.getPuntuacionParejas())
            contentValues.put(DBManager.campo7, usuario.getVidas())
            // Insertar los valores en la tabla de la base de datos y obtener el ID insertado
            insertado = dbManager.getSqLiteDatabase()!!.insert(DBManager.nombreTabla, null, contentValues)
            //!!->indica que el valor resultante no puede ser nulo
        } catch (e: SQLException) {
            // Manejo de excepciones en caso de error durante la inserción
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        } finally {
            // Cerrar la conexión con la base de datos
            dbManager.cerrar()
        }
        // Devolver true si el registro se insertó correctamente, false en caso contrario
        return insertado != -1L //si no es igual a -1 es que se ha insertado
    }
    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     *
     * @param usuario El usuario a actualizar.
     * @return true si se ha actualizado correctamente, false en caso contrario.
     */
    override fun actualizar(usuario: Usuario): Boolean {
        var actualizado = -1
        try {
            // Abrir la conexión con la base de datos
            dbManager.abrir()
            // Se crean los objetos ContentValues para almacenar los nuevos valores del usuario
            val contentValues = ContentValues()
            contentValues.put(DBManager.campo2, usuario.getContrasena())
            contentValues.put(DBManager.campo3, usuario.getPuntuacionCalculo())
            contentValues.put(DBManager.campo4, usuario.getPuntuacionIngles())
            contentValues.put(DBManager.campo5, usuario.getPuntuacionBandera())
            contentValues.put(DBManager.campo6, usuario.getPuntuacionParejas())
            contentValues.put(DBManager.campo7, usuario.getVidas())
            // Se preparan los argumentos para la cláusula WHERE de la consulta de actualización
            val args = arrayOf(usuario.getNombre())
            // Se realiza la actualización en la base de datos
            actualizado = dbManager.getSqLiteDatabase()!!
                .update(DBManager.nombreTabla, contentValues, DBManager.campo1 + "=?", args)
            //!!->indica que el valor resultante no puede ser nulo
        } catch (e: SQLException) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        } finally {
            // Cerrar la conexión con la base de datos
            dbManager.cerrar()
        }
        // Se retorna true si el número de filas actualizadas es mayor a 0, indicando que la actualización tuvo éxito
        return actualizado > 0
    }
    /**
     * Busca un usuario en la base de datos según su nombre y contraseña.
     *
     * @param usuario El usuario a buscar.
     * @return El objeto Usuario si se encuentra, null en caso contrario.
     */
    override fun buscar(usuario: Usuario): Usuario? {//Usuario? puede devolver un objeto Usuario o un null
        var usu: Usuario? = null
        var cursor: Cursor? = null
        val campos: Array<String>
        val args: Array<String>
        try {
            // Se define un array de campos que se van a seleccionar en la consulta
            campos = arrayOf(DBManager.campo1,DBManager.campo2,DBManager.campo3,DBManager.campo4,DBManager.campo5,
                DBManager.campo6,DBManager.campo7)
            // Se define un array de argumentos para la cláusula WHERE de la consulta
            args = arrayOf(usuario.getNombre(), usuario.getContrasena())
            // Se intenta abrir la conexión con la base de datos invocando el método 'abrir()' de 'dbManager'
            dbManager.abrir()
            // Se realiza la consulta en la base de datos invocando el método 'query()' de 'SQLiteDatabase'
            cursor = dbManager.getSqLiteDatabase()!!.query(//!!->indica que el valor resultante no puede ser nulo
                DBManager.nombreTabla,
                campos,
                (DBManager.campo1 + "=? AND " + DBManager.campo2).toString() + "=?",
                args,
                null,
                null,
                null
            )
            // Se verifica si el cursor contiene resultados
            if (cursor.moveToFirst()) {
                usu = Usuario(cursor.getString(0), cursor.getString(1))
                usu.setPuntuacionCalculo(cursor.getInt(2))
                usu.setPuntuacionIngles(cursor.getInt(3))
                usu.setPuntuacionBandera(cursor.getInt(4))
                usu.setPuntuacionParejas(cursor.getInt(5))
                usu.setVidas(cursor.getInt(6))
            }
        } catch (e: SQLException) {
            // En caso de excepción, se captura y no se realiza ninguna acción
        } finally {
            // Se cierra el cursor y se cierra la conexión con la base de datos invocando el método 'cerrar()' de 'dbManager'
            cursor?.close()//se cierra el cursor si no es nulo
            dbManager.cerrar()
        }
        // Se retorna el objeto Usuario encontrado o null si no se encontró ningún resultado
        return usu
    }
    /**
     * Elimina un usuario de la base de datos.
     *
     * @param usuario El usuario a eliminar.
     * @return true si se ha eliminado correctamente, false en caso contrario.
     */
    override fun eliminar(usuario: Usuario): Boolean {
        var eliminado = -1
        try {
            dbManager.abrir()
            // Preparar los argumentos para la cláusula WHERE
            val args = arrayOf(usuario.getNombre())
            // Ejecutar la operación de eliminación en la base de datos
            eliminado = dbManager.getSqLiteDatabase()!!.delete(DBManager.nombreTabla, DBManager.campo1 + "=?", args)
            //!!->indica que el dbManager.getSqLiteDatabase()!! no puede ser nulo, si lo es lanzará una excepción
        } catch (e: SQLException) {
            // Manejar cualquier excepción de SQL y mostrar un mensaje de error
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        } finally {
            // Cerrar la conexión a la base de datos
            dbManager.cerrar()
        }
        // Devolver true si se eliminó al menos un registro, de lo contrario, false
        return eliminado > 0
    }
}