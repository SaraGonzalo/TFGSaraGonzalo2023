package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 3 del juego de las banderas.
 * Extiende la clase base "JuegoBandera".
 */
class BanderaNivelTres : JuegoBandera() {
    private lateinit var pais: String
    private lateinit var paises: Array<String>
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
        Toast.makeText(this, "NIVEL 3: BANDERAS DEL MUNDO", Toast.LENGTH_SHORT).show()
        paises = arrayOf(
            "estados unidos", "brasil", "argentina", "china", "japón", "uruguay", "colombia", "canadá",
            "india", "australia", "marruecos", "méxico", "sudáfrica", "túnez", "arabia saudí", "argelia", "ghana",
            "chile", "cuba", "ecuador", "egipto", "honduras", "jamaica", "panamá", "paraguay", "perú", "corea del norte",
            "corea del sur", "singapur", "tailandia", "venezuela"
        )
        bandera = arrayOf(
            "estadosunidos", "brasil", "argentina", "china", "japon", "uruguay", "colombia", "canada",
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
        var numeroUno: Int
        var id1: Int
        if (puntuacionBandera < 30) {
            numeroUno = (Math.random() * 31).toInt()
            pais = paises.get(numeroUno)
            id1 = resources.getIdentifier(bandera?.get(numeroUno), "drawable", packageName)
            imgBandera.setImageResource(id1)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, BanderaNivelCuatro::class.java)
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