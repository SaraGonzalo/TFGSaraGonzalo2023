package com.example.myapplication.aplicacion
import android.os.Handler
import android.widget.ImageView
/**
 * Clase que permite cambiar la imagen de un ImageView de forma periódica.
 *
 * @param personaje El ImageView en el que se cambiará la imagen.
 * @param personajes Un array de enteros que representa los recursos de las imágenes a mostrar.
 * @param handler El objeto Handler utilizado para programar las ejecuciones periódicas.
 */
class CambioImagen(private val personaje: ImageView, private val personajes: IntArray, private val handler: Handler) : Runnable {
    private var numero = 0
    /**
     * Ejecuta el cambio de imagen.
     * @return No devuelve ningún valor.
     */
    override fun run() {
        numero++
        if (numero > 4) {
            numero = 0
        }
        personaje.setImageResource(personajes[numero])
        //El método postDelayed() toma dos argumentos: un objeto Runnable y un tiempo en milisegundos. Cuando se llama a este método, el objeto Handler espera el tiempo especificado y luego ejecuta el método run() del objeto Runnable pasado como argumento.
        handler.postDelayed(this, 2000) //programar la siguiente ejecución del método run().
    }
    /**
     * Inicia el cambio de imágenes.
     * @return No devuelve ningún valor.
     */
    fun start() { //programar la primera ejecución del método run().
        handler.postDelayed(this, 2000)
    }
    /**
     * Detiene el cambio de imágenes.
     * @return No devuelve ningún valor.
     */
    fun stop() { //detener el cambio de imágenes.
        handler.removeCallbacks(this)
    }
}