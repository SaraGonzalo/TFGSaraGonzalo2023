package com.example.myapplication.aplicacion
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el nivel uno del juego de inglés.
 * Extiende de la clase abstracta JuegoIngles.
 * Proporciona funcionalidad específica para el nivel uno, relacionado con los números en inglés.
 */
class InglesNivelUno : JuegoIngles() {
    private lateinit var numeros: Array<String>
    private lateinit var numbers: Array<String>
    private lateinit var resultado: String
    private lateinit var traduccion: Array<String>
    private lateinit var palabra: String
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "LEVEL 1: NUMBERS", Toast.LENGTH_SHORT).show()
        numeros = arrayOf(
            "cero", "uno", "numdos", "numtres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once",
            "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "dieciocho", "diecinueve", "veinte",
            "veintiuno", "veintidos", "veintitres", "veinticuatro", "veinticinco", "veintiseis", "veintisiete",
            "veintiocho"
        )
        numbers = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "twenty one",
            "twenty two", "twenty three", "twenty four", "twenty five", "twenty six", "twenty seven", "twenty eight"
        )
        traduccion = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "twentyone",
            "twentytwo", "twentythree", "twentyfour", "twentyfive", "twentysix", "twentyseven", "twentyeight"
        )
        aleatorio()
    }
    /**
     * Método para generar un número aleatorio y mostrar la imagen y palabra correspondientes.
     * Si la puntuación de inglés es =10, se pasa al siguiente nivel.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionIngles < 10) {
            numeroUno = (Math.random() * 29).toInt()
            resultado = numbers[numeroUno]
            palabra = traduccion[numeroUno]
            id1 = resources.getIdentifier(numeros[numeroUno], "drawable", packageName)
            imgIngles.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, InglesNivelDos::class.java)
            intent.putExtra("jugador", nombre)
            intent.putExtra("contrasena", contrasena)
            intent.putExtra("puntuacionCalculo", puntuacionCalculo.toString())
            intent.putExtra("puntuacionIngles", puntuacionIngles.toString())
            intent.putExtra("puntuacionParejas", puntuacionParejas.toString())
            intent.putExtra("puntuacionBandera", puntuacionBandera.toString())
            intent.putExtra("numVidas", numVidas.toString())
            startActivity(intent)
        }
    }
    /**
     * Método para comprobar la respuesta del jugador.
     * Invoca al método comprobar de la clase base JuegoIngles, pasando el resultado esperado.
     * @param view La vista desde la cual se invocó el método.
     * @return No devuelve ningún valor.
     */
    fun comprobar(view: View) {
        super.comprobar(resultado)
    }
    /**
     * Método para reproducir la pista de audio asociada a la palabra.
     * Invoca al método pista de la clase base JuegoIngles, pasando la palabra correspondiente.
     * @param view La vista desde la cual se invocó el método.
     * @return No devuelve ningún valor.
     */
    fun pista(view: View) {
        super.pista(palabra)
    }
}
