package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 4 del juego de las banderas.
 * Extiende la clase base "JuegoBandera".
 */
class BanderaNivelCuatro : JuegoBandera() {
    private lateinit var pais: String
    private lateinit var paises: Array<String>
    private lateinit var bandera: Array<String>
    /**
     * Método que se ejecuta al crear la actividad del nivel 4.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 4: BANDERAS DE ESPAÑA Y EL MUNDO", Toast.LENGTH_SHORT).show()
        paises = arrayOf("aragón", "andalucía", "asturias", "cantabria", "ceuta",
            "cataluña", "castilla la mancha", "castilla y león", "comunidad valenciana", "extremadura", "galicia",
            "islas baleares", "islas canarias", "la rioja", "madrid", "murcia", "navarra",
            "melilla", "país vasco", "españa", "portugal", "francia", "alemania", "bélgica", "países bajos", "república checa",
            "croacia", "finlandia", "italia", "estonia", "letonia", "lituania", "irlanda", "dinamarca", "rusia", "ucrania",
            "eslovaquia", "turquía", "rumanía", "georgia", "austria", "grecia", "reino unido", "gales", "polonia", "suecia",
            "hungría", "suiza", "macedonia", "bosnia y herzegovina", "albania", "montenegro", "islandia", "malta", "andorra",
            "ciudad del vaticano", "san marino", "estados unidos", "brasil", "argentina", "china", "japón", "uruguay", "colombia", "canadá",
            "india", "australia", "marruecos", "méxico", "sudáfrica", "túnez", "arabia saudí", "argelia", "ghana",
            "chile", "cuba", "ecuador", "egipto", "honduras", "jamaica", "panamá", "paraguay", "perú", "corea del norte",
            "corea del sur", "singapur", "tailandia", "venezuela"
        )
        bandera = arrayOf("aragon", "andalucia", "asturias", "cantabria", "ceuta", "cataluna", "castillalamancha",
            "castillayleon", "comunidadvalenciana", "extremadura", "galicia", "islasbaleares", "islascanarias", "larioja", "madrid",
            "murcia", "navarra", "melilla", "paisvasco", "espana", "portugal", "francia", "alemania", "belgica", "paisesbajos", "republicacheca",
            "croacia", "finlandia", "italia", "estonia", "letonia", "lituania", "irlanda", "dinamarca", "rusia", "ucrania",
            "eslovaquia", "turquia", "rumania", "georgia", "austria", "grecia", "reinounido", "gales", "polonia", "suecia",
            "hungria", "suiza", "macedonia", "bosniayherzegovina", "albania", "montenegro", "islandia", "malta", "andorra",
            "ciudaddelvaticano", "sanmarino", "estadosunidos", "brasil", "argentina", "china", "japon", "uruguay", "colombia", "canada",
            "india", "australia", "marruecos", "mexico", "sudafrica", "tunez", "arabiasaudi", "argelia", "ghana",
            "chile", "cuba", "ecuador", "egipto", "honduras", "jamaica", "panama", "paraguay", "peru", "coreadelnorte",
            "coreadelsur", "singapur", "tailandia", "venezuela"
        )
        aleatorio()
    }
    /**
     * Genera un número aleatorio y muestra las imagen correspondiente.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionBandera < 2000000) {
            numeroUno = (Math.random() * 88).toInt()
            pais = paises[numeroUno]
            id1 = resources.getIdentifier(bandera[numeroUno], "drawable", packageName)
            imgBandera.setImageResource(id1)
        } else {
            Toast.makeText(this, "TE HAS PASADO EL JUEGO", Toast.LENGTH_SHORT).show()
            mp.stop()
            mp.release()
            val intent = Intent(this, MainActivity::class.java)
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
    fun comprobar(view: View?) {
        super.comprobar(pais)
    }
}