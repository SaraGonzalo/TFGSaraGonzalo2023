package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el nivel cuatro del juego de inglés.
 * Extiende de la clase abstracta JuegoIngles.
 * Proporciona funcionalidad específica para el nivel cuatro, relacionado con los países en inglés.
 */
class InglesNivelCuatro : JuegoIngles() {
    private lateinit var paises: Array<String>
    private lateinit var countries: Array<String>
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
        Toast.makeText(this, "LEVEL 4: COUNTRIES", Toast.LENGTH_SHORT).show()
        // nombre de las imágenes
        paises = arrayOf(
            "albania", "argelia", "argentina", "australia", "austria", "belgica", "bosnia y herzegovina",
            "brasil", "dinamarca", "ecuador", "egipto", "estonia", "finlandia", "francia", "georgia",
            "alemania", "ghana", "grecia", "honduras", "hungria", "islandia", "india", "irlanda", "italia",
            "jamaica", "japon", "letonia", "lituania", "mexico", "marruecos", "paisesbajos", "coreadelnorte",
            "panama", "paraguay", "peru", "polonia", "portugal", "rumania", "rusia", "arabiasaudi", "singapur",
            "eslovaquia", "sudafrica", "coreadelsur", "espana", "suecia", "suiza", "tailandia", "tunez", "ucrania",
            "reinounido", "estadosunidos", "uruguay", "gales"
        )
        // nombre del país en inglés
        countries = arrayOf(
            "albania", "algeria", "argentina", "australia", "austria", "belgium", "bosnia and herzegovina",
            "brazil", "denmark", "ecuador", "egypt", "estonia", "finland", "france", "georgia", "germany",
            "ghana", "greece", "honduras", "hungary", "iceland", "india", "ireland", "italy", "jamaica", "japan",
            "latvia", "lithuania", "mexico", "morocco", "netherlands", "north korea", "panama", "paraguay", "peru",
            "poland", "portugal", "romania", "russia", "saudi arabia", "singapore", "slovakia", "south africa",
            "south korea", "spain", "sweden", "switzerland", "thailand", "tunisia", "ukraine", "united kingdom",
            "united states", "uruguay", "welsh"
        )
        // nombre del audio
        traduccion = arrayOf(
            "albania", "algeria", "argentina", "australia", "austria", "belgium", "bosniaandherzegovina", "brazil",
            "denmark", "ecuador", "egypt", "estonia", "finland", "france", "georgia", "germany", "ghana", "greece",
            "honduras", "hungary", "iceland", "india", "ireland", "italy", "jamaica", "japan", "latvia", "lithuania",
            "mexico", "morocco", "netherlands", "northkorea", "panama", "paraguay", "peru", "poland", "portugal",
            "romania", "russia", "saudiarabia", "singapore", "slovakia", "southafrica", "southkorea", "spain", "sweden",
            "switzerland", "thailand", "tunisia", "ukraine", "unitedkingdom", "unitedstates", "uruguay", "welsh"
        )
        aleatorio()
    }
    /**
     * Método para generar un número aleatorio y mostrar la imagen y palabra correspondientes.
     * Si la puntuación de inglés es =40, se pasa al siguente nivel
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionIngles < 40) {
            numeroUno = (Math.random() * 54).toInt()
            resultado = countries[numeroUno]
            palabra = traduccion[numeroUno]
            id1 = resources.getIdentifier(paises[numeroUno], "drawable", packageName)
            imgIngles.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, InglesNivelCinco::class.java)
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
