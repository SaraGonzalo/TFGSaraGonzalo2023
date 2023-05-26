package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el nivel tres del juego de inglés.
 * Extiende de la clase abstracta JuegoIngles.
 * Proporciona funcionalidad específica para el nivel tres, relacionado con los vehículos en inglés.
 */
class InglesNivelTres : JuegoIngles() {
    private lateinit var vehiculos: Array<String>
    private lateinit var vehicles: Array<String>
    private lateinit var resultado: String
    private lateinit var palabra: String
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "LEVEL 3: VEHICLES", Toast.LENGTH_SHORT).show()
        vehiculos = arrayOf(
            "bicycle", "boat", "bus", "camper", "canoe", "car", "crane", "helicopter", "hot air balloon",
            "lorry", "motorbike", "plane", "tractor", "train", "tram", "underground", "underwater", "van"
        )
        vehicles = arrayOf(
            "bicycle", "boat", "bus", "camper", "canoe", "car", "crane", "helicopter", "hotairballoon",
            "lorry", "motorbike", "plane", "tractor", "train", "tram", "underground", "underwater", "van"
        )
        aleatorio()
    }
    /**
     * Método para generar un número aleatorio y mostrar la imagen y palabra correspondientes.
     * Si la puntuación de inglés es =30, se pasa al siguiente nivel.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionIngles < 30) {
            numeroUno = (Math.random() * 18).toInt()
            resultado = vehiculos[numeroUno]
            palabra = vehicles[numeroUno]
            id1 = resources.getIdentifier(palabra, "drawable", packageName)
            imgIngles.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, InglesNivelCuatro::class.java)
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
