package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el Nivel 3 del juego de cálculo.
 * Extiende la clase base "JuegoCalculo".
 */
class CalculoNivelTres : JuegoCalculo() {
    private var resultado: Int = 0
    private lateinit var operaciones: Array<String>
    /**
     * Método que se ejecuta al crear la actividad del nivel 3.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 3: SUMAS Y RESTAS", Toast.LENGTH_SHORT).show()
        operaciones = arrayOf("suma", "resta")
        aleatorio()
    }
    /**
     * Genera dos números aleatorios y muestra las imágenes correspondientes.
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        var numeroUno: Int
        var numeroDos: Int
        var id1: Int
        var id2: Int
        var idOperacion: Int
        var id3: Int
        if (puntuacionCalculo < 30) {
            numeroUno = (Math.random() * 29).toInt()
            numeroDos = (Math.random() * 29).toInt()
            idOperacion = (Math.random() * 2).toInt()
            when (idOperacion) {
                0 -> resultado = numeroUno + numeroDos
                1 -> resultado = numeroUno - numeroDos
            }
            if (resultado >= 0) {
                id1 = resources.getIdentifier(numeros[numeroUno], "drawable", packageName)
                id2 = resources.getIdentifier(numeros[numeroDos], "drawable", packageName)
                id3 = resources.getIdentifier(operaciones[idOperacion], "drawable", packageName)
                imgNumUno.setImageResource(id1)
                imgNumDos.setImageResource(id2)
                imgOperacion.setImageResource(id3)
            } else {
                aleatorio()
            }
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, CalculoNivelCuatro::class.java)
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