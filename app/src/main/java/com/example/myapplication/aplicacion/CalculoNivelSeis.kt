package com.example.myapplication.aplicacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myapplication.R
/**
 * Clase que representa el Nivel 6 del juego de cálculo.
 * Extiende la clase base "JuegoCalculo".
 */
class CalculoNivelSeis : JuegoCalculo() {
    private var resultado: Int = 0
    /**
     * Método que se ejecuta al crear la actividad del nivel 6.
     * Muestra un mensaje Toast indicando el nivel y llama al método "aleatorio".
     *
     * @param savedInstanceState El estado anterior de la actividad, si está disponible.
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "NIVEL 6: DIVISIONES", Toast.LENGTH_SHORT).show()
        imgOperacion.setImageResource(R.drawable.division)
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
        if (puntuacionCalculo < 60) {
            numeroUno = (Math.random() * 29).toInt()
            numeroDos = (Math.random() * 29).toInt()
            if (numeroDos != 0) {
                resultado = numeroUno / numeroDos
                id1 = resources.getIdentifier(numeros[numeroUno], "drawable", packageName)
                id2 = resources.getIdentifier(numeros[numeroDos], "drawable", packageName)
                imgNumUno.setImageResource(id1)
                imgNumDos.setImageResource(id2)
            } else {
                aleatorio()
            }
        } else {
            mp.stop()
            mp.release()
            val intent = Intent(this, CalculoNivelSiete::class.java)
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