package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el nivel cinco del juego de inglés.
 * Extiende de la clase abstracta JuegoIngles.
 * Proporciona funcionalidad específica para el nivel cinco, relacionado con la comida en inglés.
 */
class InglesNivelCinco : JuegoIngles() {
    private lateinit var comidas: Array<String>
    private lateinit var resultado: String
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "LEVEL 5: FOOD", Toast.LENGTH_SHORT).show()
        // nombre de las imágenes
        comidas = arrayOf(
            "apple", "asparagus", "aubergine", "banana", "blueberry", "bread", "carrot", "cheese", "cherry",
            "chocolate", "corn", "egg", "garlic", "grape", "hamburguer", "kiwi", "lemon", "melon",
            "milk", "mushroom", "onion", "orange", "pear", "pepper", "pineapple", "pizza", "potato", "raspberry",
            "rice", "strawberry", "tomato", "water", "watermelon"
        )
        aleatorio()
    }
    /**
     * Método para generar un número aleatorio y mostrar la imagen y palabra correspondientes.
     * Si la puntuación de inglés es =50, se pasa al siguente nivel
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionIngles < 50) {
            numeroUno = (Math.random() * 33).toInt()
            resultado = comidas[numeroUno]
            id1 = resources.getIdentifier(resultado, "drawable", packageName)
            imgIngles.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, InglesNivelSeis::class.java)
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
        super.pista(resultado)
    }
}
