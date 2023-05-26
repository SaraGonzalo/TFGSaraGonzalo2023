package com.example.myapplication.aplicacion
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.widget.AppCompatButton
/**
 * Clase personalizada que representa un botón con una imagen.
 *
 * @param context El contexto de la aplicación.
 * @param drawable El recurso de la imagen a mostrar en el botón.
 */
class MiBoton(context: Context, drawable: Int) : AppCompatButton(context) {
    private var imagen: Int = drawable
    /**
     * Inicializa el botón con los valores predeterminados y la imagen proporcionada.
     */
    init {
        this.text = "?" // Establece el texto del botón como "?"
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat()) // Establece el tamaño del texto del botón
        this.setBackgroundColor(Color.rgb(90, 204, 31)) // Establece el color de fondo del botón
        this.setTextColor(Color.WHITE) // Establece el color del texto del botón
        val displayMetrics = resources.displayMetrics//obtiene una referencia a las medidas del dispositivo
        val width = displayMetrics.widthPixels//asigna el valor de la anchura de la pantalla en píxeles a la variable width
        val params = LayoutParams((width * 0.15).toInt(), (width * 0.15).toInt()) // Establece los parámetros de diseño del botón, en este caso, el ancho y alto
        this.layoutParams = params//asigna los parámetros al botón
        this.establecerMargenes(8, 8, 8, 8) // Establece los márgenes del botón utilizando el método establecerMargenes
    }
    /**
     * Establece los márgenes del botón.
     *
     * @param left El margen izquierdo en píxeles.
     * @param top El margen superior en píxeles.
     * @param right El margen derecho en píxeles.
     * @param bottom El margen inferior en píxeles.
     * @return No devuelve ningún valor.
     */
    fun establecerMargenes(left: Int, top: Int, right: Int, bottom: Int) {//establecer los márgenes
        // Obtener los parámetros de diseño existentes del objeto actual
        val params = this.layoutParams as LayoutParams
        // Establecer los márgenes en los parámetros de diseño
        params.setMargins(left, top, right, bottom)
        // Asignar los nuevos parámetros de diseño al objeto actual
        this.layoutParams = params
    }
    /**
     * Obtiene el recurso de imagen asociado al botón.
     *
     * @return El recurso de imagen.
     */
    fun getImagen(): Int{
        return(imagen)
    }
}