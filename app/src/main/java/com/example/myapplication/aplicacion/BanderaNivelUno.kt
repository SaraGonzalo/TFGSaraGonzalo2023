package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 1 del juego de las banderas.
 * Extiende la clase base "JuegoBandera".
 */
class BanderaNivelUno : JuegoBandera() {
    private lateinit var comunidad: String
    private lateinit var comunidades: Array<String>
    private lateinit var bandera: Array<String>
    /**
     * Método que se ejecuta al crear la actividad del nivel 1.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 1: COMUNIDADES Y CIUDADES AUTÓNOMAS", Toast.LENGTH_SHORT).show()
        comunidades = arrayOf(
            "aragón", "andalucía", "principado de asturias", "cantabria", "ceuta",
            "cataluña", "castilla la mancha", "castilla y león", "comunidad valenciana", "extremadura", "galicia",
            "islas baleares", "islas canarias", "la rioja", "comunidad de madrid", "región de murcia", "comunidad foral de navarra",
            "melilla", "país vasco"
        )
        bandera = arrayOf(
            "aragon", "andalucia", "asturias", "cantabria", "ceuta", "cataluna", "castillalamancha",
            "castillayleon", "comunidadvalenciana", "extremadura", "galicia", "islasbaleares", "islascanarias", "larioja", "madrid",
            "murcia", "navarra", "melilla", "paisvasco"
        )
        aleatorio()
    }
    /**
     * Genera un número aleatorio y muestra las imagen correspondiente.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        var numeroUno: Int
        var id1: Int
        if (puntuacionBandera < 10) {
            numeroUno = (Math.random() * 19).toInt()
            comunidad = comunidades.get(numeroUno)
            id1 = resources.getIdentifier(bandera?.get(numeroUno), "drawable", packageName)
            imgBandera.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, BanderaNivelDos::class.java)
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
     * Método que se llama al hacer clic en un botón para comprobar la respuesta.
     * Llama al método "comprobar" de la superclase, pasando el resultado actual.
     *
     * @param view La vista del botón que se ha hecho clic.
     * @return No devuelve ningún valor.
     */
    fun comprobar(view: View) {
        super.comprobar(comunidad)
    }
}