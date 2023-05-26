package com.example.myapplication.aplicacion

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
/**
 * Actividad que muestra el menú de minijuegos y gestiona la navegación entre ellos.
 */
class Minijuego : AppCompatActivity() {
    //variables declaradas con var puede variar su valor a lo largo del código
    //variables declaradas con val no varía su valor a lo largo del código
    //lateinit->declarar una variable que se inicializará más adelante antes de acceder a ella por primera vez.
    private var nombre: String = ""
    private var puntuacionCalculo: String = ""
    private var puntuacionIngles: String = ""
    private var puntuacionBandera: String = ""
    private var puntuacionParejas: String = ""
    private var numVidas: String = ""
    private var contrasena: String = ""
    private lateinit var txtJugador: TextView
    private lateinit var txtPuntuacion: TextView
    private lateinit var imgVidas: ImageView
    private lateinit var mp: MediaPlayer
    /**
     * Método de creación de la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minijuego)

        // Personalizar ActionBar
        supportActionBar?.apply {//supportActionBar->permite acceder a la ActionBar de la actividad
            //permite acceder al actionBar si no es nulo
            //apply->permite realizar múltiples operaciones en el objeto receptor (en este caso, supportActionBar) sin tener que repetir su nombre.
            setDisplayShowHomeEnabled(true)//configura la visualización del ícono de inicio en la ActionBar.
            setIcon(R.mipmap.ic_launcher)//establece el ícono de la aplicación en la ActionBar
            //setBackground->establece el fondo de la ActionBar
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@Minijuego, R.color.minijuegos)))
            //this@Minijuego->se refiere al contexto de la actividad actual, que es Minijuego
        }

        // Cargar datos de la actividad anterior
        nombre = intent.getStringExtra("jugador") ?: "" //proporcionar un valor de respaldo en caso de que el resultado de la expresión antes del ?: sea nulo.
        //intent.getStringExtra("jugador") devuelve un valor no nulo, se asigna ese valor a la variable
        contrasena = intent.getStringExtra("contrasena") ?: ""
        puntuacionBandera = intent.getStringExtra("puntuacionBandera") ?: ""
        puntuacionParejas = intent.getStringExtra("puntuacionParejas") ?: ""
        puntuacionIngles = intent.getStringExtra("puntuacionIngles") ?: ""
        puntuacionCalculo = intent.getStringExtra("puntuacionCalculo") ?: ""
        numVidas = intent.getStringExtra("numVidas") ?: ""

        //obtener una referencia a los elementos de la interfaz de usuario
        txtJugador = findViewById(R.id.txtJugador)
        txtPuntuacion = findViewById(R.id.txtPuntuacion)
        imgVidas = findViewById(R.id.imgVidas)

        // Actualizar vistas con los datos cargados
        colocarDatos()

        // Crear MediaPlayer
        mp = MediaPlayer.create(this, R.raw.minijuego)
        mp.start()
        mp.isLooping = true//para que se vuelva a reproducir cuando acabe
    }
    /**
     * Método invocado al hacer clic en el botón del juego de cálculo.
     * Navega al nivel correspondiente según la puntuación.
     * @return No devuelve ningún valor.
     */
    fun calculo(view: View) {
        //en función de la puntuación irá a un nivel o a otro
        var intent: Intent? = null
        val puntuacion = puntuacionCalculo.toInt()
        //en función de la puntuación de cálculo carga una actividad u otra
        intent = when {
            puntuacion < 10 -> Intent(this, CalculoNivelUno::class.java)
            puntuacion < 20 -> Intent(this, CalculoNivelDos::class.java)
            puntuacion < 30 -> Intent(this, CalculoNivelTres::class.java)
            puntuacion < 40 -> Intent(this, CalculoNivelCuatro::class.java)
            puntuacion < 50 -> Intent(this, CalculoNivelCinco::class.java)
            puntuacion < 60 -> Intent(this, CalculoNivelSeis::class.java)
            puntuacion < 70 -> Intent(this, CalculoNivelSiete::class.java)
            puntuacion < 80 -> Intent(this, CalculoNivelOcho::class.java)
            else -> Intent(this, CalculoNivelNueve::class.java)
        }
        startActivity(siguienteActividad(intent))
    }
    /**
     * Método invocado al hacer clic en el botón del juego de inglés.
     * Navega al nivel correspondiente según la puntuación.
     * @return No devuelve ningún valor.
     */
    fun ingles(view: View){
        var intent: Intent? = null
        val puntuacion = puntuacionIngles.toInt()
        //en función de la puntuación de inglés carga una actividad u otra
        intent = when {
            puntuacion < 10 -> Intent(this, InglesNivelUno::class.java)
            puntuacion < 20 -> Intent(this, InglesNivelDos::class.java)
            puntuacion < 30 -> Intent(this, InglesNivelTres::class.java)
            puntuacion < 40 -> Intent(this, InglesNivelCuatro::class.java)
            puntuacion < 50 -> Intent(this, InglesNivelCinco::class.java)
            else -> Intent(this, InglesNivelSeis::class.java)
        }
        startActivity(siguienteActividad(intent))
    }
    /**
     * Método invocado al hacer clic en el botón del juego de parejas.
     * Navega al juego de parejas.
     * @return No devuelve ningún valor.
     */
    fun pareja(view: View){
        var intent: Intent? = null
        intent = Intent(this, JuegoParejas::class.java)
        startActivity(siguienteActividad(intent))
    }
    /**
     * Método invocado al hacer clic en el botón del juego de banderas.
     * Navega al nivel correspondiente según la puntuación.
     * @return No devuelve ningún valor.
     */
    fun bandera(view: View){
        var intent: Intent? = null
        val puntuacion = puntuacionBandera.toInt()
        //en función de la puntuación de banderas carga una actividad u otra
        intent = when {
            puntuacion < 10 -> Intent(this, BanderaNivelUno::class.java)
            puntuacion < 20 -> Intent(this, BanderaNivelDos::class.java)
            puntuacion < 30 -> Intent(this, BanderaNivelTres::class.java)
            else -> Intent(this, BanderaNivelCuatro::class.java)
        }
        startActivity(siguienteActividad(intent))
    }
    /**
     * Prepara el intent para pasar a la siguiente actividad.
     * Actualiza el intent con los datos del jugador y detiene el reproductor de música.
     *
     * @param intent El intent para la siguiente actividad.
     * @return El intent actualizado.
     */
    fun siguienteActividad(intent: Intent): Intent {
        //pasar los datos a la siguiente actividad
        intent.putExtra("jugador", nombre)
        intent.putExtra("contrasena", contrasena)
        intent.putExtra("puntuacionCalculo", puntuacionCalculo)
        intent.putExtra("puntuacionIngles", puntuacionIngles)
        intent.putExtra("puntuacionParejas", puntuacionParejas)
        intent.putExtra("puntuacionBandera", puntuacionBandera)
        intent.putExtra("numVidas", numVidas)
        mp.stop()
        mp.release()
        return(intent)
    }
    /**
     * Actualiza las vistas con los datos cargados.
     * @return No devuelve ningún valor.
     */
    private fun colocarDatos() {
        //actualizar la vista con los datos correspondientes
        val puntuacionTotal = puntuacionCalculo.toInt() + puntuacionBandera.toInt() + puntuacionIngles.toInt() + puntuacionParejas.toInt()
        txtJugador.text = nombre
        txtPuntuacion.text = puntuacionTotal.toString()
        val vidas = numVidas.toInt()
        when (vidas) {
            1 -> imgVidas.setImageResource(R.drawable.una)
            2 -> imgVidas.setImageResource(R.drawable.dos)
            3 -> imgVidas.setImageResource(R.drawable.tres)
        }
    }
    /**
     * Manejador del evento onBackPressed.
     *
     * Este método se ejecuta cuando se presiona el botón de retroceso en el dispositivo.
     * Inicia una nueva actividad MainActivity utilizando un intent para volver a la pantalla principal.
     *
     * @return No devuelve ningún valor.
     */
    override fun onBackPressed() {
        //este método se ejecuta cuando se presiona el botón de ir hacia atrás en el teléfono
        mp.stop()
        mp.release()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}