package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 1 del juego de cálculo.
 * Extiende la clase base "JuegoCalculo".
 */
class CalculoNivelUno : JuegoCalculo() {
    private var resultado: Int = 0
    /**
     * Método que se ejecuta al crear la actividad del nivel 1.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 1: SUMAS", Toast.LENGTH_SHORT).show()
        aleatorio()
    }
    /**
     * Genera dos números aleatorios y muestra las imágenes correspondientes.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val numeroDos: Int
        var id1: Int
        var id2: Int
        if (puntuacionCalculo < 10) {
            numeroUno = (Math.random() * 29).toInt()
            numeroDos = (Math.random() * 29).toInt()
            resultado = numeroUno + numeroDos
            id1 = resources.getIdentifier(numeros[numeroUno], "drawable", packageName)
            id2 = resources.getIdentifier(numeros[numeroDos], "drawable", packageName)
            imgNumUno.setImageResource(id1)
            imgNumDos.setImageResource(id2)
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, CalculoNivelDos::class.java)
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
        super.comprobar(resultado)
    }
}