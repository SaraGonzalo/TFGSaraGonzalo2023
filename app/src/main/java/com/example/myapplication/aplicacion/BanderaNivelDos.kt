package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 2 del juego de las banderas.
 * Extiende la clase base "JuegoBandera".
 */
class BanderaNivelDos : JuegoBandera() {
    private lateinit var pais: String
    private lateinit var paises: Array<String>
    private lateinit var bandera: Array<String>
    /**
     * Método que se ejecuta al crear la actividad del nivel 2.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 2: BANDERAS DE EUROPA", Toast.LENGTH_SHORT).show()
        paises = arrayOf(
            "españa", "portugal", "francia", "alemania", "bélgica", "países bajos", "república checa",
            "croacia", "finlandia", "italia", "estonia", "letonia", "lituania", "irlanda", "dinamarca", "rusia", "ucrania",
            "eslovaquia", "turquía", "rumanía", "georgia", "austria", "grecia", "reino unido", "gales", "polonia", "suecia",
            "hungría", "suiza", "macedonia", "bosnia y herzegovina", "albania", "montenegro", "islandia", "malta", "andorra",
            "ciudad del vaticano", "san marino"
        )
        bandera = arrayOf(
            "espana", "portugal", "francia", "alemania", "belgica", "paisesbajos", "republicacheca",
            "croacia", "finlandia", "italia", "estonia", "letonia", "lituania", "irlanda", "dinamarca", "rusia", "ucrania",
            "eslovaquia", "turquia", "rumania", "georgia", "austria", "grecia", "reinounido", "gales", "polonia", "suecia",
            "hungria", "suiza", "macedonia", "bosniayherzegovina", "albania", "montenegro", "islandia", "malta", "andorra",
            "ciudaddelvaticano", "sanmarino"
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
        if (puntuacionBandera < 20) {
            numeroUno = (Math.random() * 38).toInt()
            pais = paises.get(numeroUno)
            id1 = resources.getIdentifier(bandera?.get(numeroUno), "drawable", packageName)
            imgBandera.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, BanderaNivelTres::class.java)
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
        super.comprobar(pais)
    }
}