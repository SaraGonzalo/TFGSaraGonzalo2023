package com.example.myapplication.aplicacion
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.modelodatos.Usuario
import com.example.myapplication.persistencia.UsuarioDAO
/**
 * Clase abstracta base para el juego de cálculo.
 * Extiende de AppCompatActivity.
 * Proporciona funcionalidad común y métodos abstractos para los juegos de cálculo.
 */
abstract class JuegoCalculo : AppCompatActivity() {
    //variables declaradas con var puede variar su valor a lo largo del código
    //variables declaradas con val no varía su valor a lo largo del código
    //lateinit->declarar una variable que se inicializará más adelante antes de acceder a ella por primera vez.
    protected lateinit var numeros: Array<String>
    protected lateinit var mp: MediaPlayer
    private lateinit var correcto: MediaPlayer
    private lateinit var incorrecto: MediaPlayer
    private lateinit var txtJugador: TextView
    private lateinit var txtPuntuacion: TextView
    protected lateinit var imgVidas: ImageView
    protected lateinit var imgNumUno: ImageView
    protected lateinit var imgNumDos: ImageView
    private lateinit var txtEvaluar: EditText
    protected lateinit var imgOperacion: ImageView
    protected lateinit var nombre: String
    protected lateinit var contrasena: String
    protected var puntuacionCalculo: Int = 0
    protected var puntuacionBandera: Int = 0
    protected var puntuacionIngles: Int = 0
    protected var puntuacionParejas: Int = 0
    protected var numVidas: Int = 0
    private lateinit var usuario: Usuario
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculo)
        // Configuración de la barra de acción
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.calculo)))

        numeros = arrayOf(
            "cero", "uno", "numdos", "numtres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "dieciocho",
            "diecinueve", "veinte", "veintiuno", "veintidos", "veintitres", "veinticuatro", "veinticinco",
            "veintiseis", "veintisiete", "veintiocho")

        txtJugador = findViewById(R.id.txtJugador)
        txtPuntuacion = findViewById(R.id.txtPuntuacion)
        imgVidas = findViewById(R.id.imgVidas)
        imgNumUno = findViewById(R.id.imgNumUno)
        imgNumDos = findViewById(R.id.imgNumDos)
        txtEvaluar = findViewById(R.id.txtCalculo)
        imgOperacion = findViewById(R.id.imgOperacion)

        // Datos que vienen de la actividad anterior
        nombre = intent.getStringExtra("jugador") ?: ""
        contrasena = intent.getStringExtra("contrasena") ?: ""
        puntuacionCalculo = intent.getStringExtra("puntuacionCalculo")?.toInt() ?: 0
        puntuacionIngles = intent.getStringExtra("puntuacionIngles")?.toInt() ?: 0
        puntuacionBandera = intent.getStringExtra("puntuacionBandera")?.toInt() ?: 0
        puntuacionParejas = intent.getStringExtra("puntuacionParejas")?.toInt() ?: 0
        numVidas = intent.getStringExtra("numVidas")?.toInt() ?: 0
        usuario = Usuario(nombre,contrasena,puntuacionCalculo,puntuacionIngles,
            puntuacionBandera,puntuacionParejas,numVidas)

        mp = MediaPlayer.create(this, R.raw.calculo)
        mp.start()
        mp.isLooping = true

        correcto = MediaPlayer.create(this, R.raw.acierto)
        incorrecto = MediaPlayer.create(this, R.raw.error)
        colocarDatos()
    }
    /**
     * Método abstracto para generar un valor aleatorio o realizar acciones específicas del juego.
     * Debe ser implementado por las subclases.
     * @return No devuelve ningún valor.
     */
    abstract fun aleatorio()
    /**
     * Método para comprobar la respuesta del jugador.
     * Verifica si la respuesta es correcta o incorrecta y actualiza la puntuación y el estado del juego en consecuencia.
     * @param resultado La respuesta correcta esperada.
     * @return No devuelve ningún valor.
     */
    fun comprobar(resultado: Int) {
        //método que comprueba si el resultado coincide con el valor introducido
        var numero: Int
        val handler = Handler(Looper.getMainLooper())
        val usuarioDAO = UsuarioDAO(this)
        val respuesta = txtEvaluar.text.toString()
        if (respuesta.isNotEmpty()) {
            numero = respuesta.toInt()
            if (numero == resultado) {
                // Audio de respuesta correcta
                correcto.start()
                puntuacionCalculo++
            } else {
                // Audio de respuesta incorrecta
                incorrecto.start()
                numVidas--
                when (numVidas) {
                    1 -> {
                        Toast.makeText(this, "TE QUEDA UNA VIDA", Toast.LENGTH_SHORT).show()
                        imgVidas.setImageResource(R.drawable.una)
                    }
                    2 -> {
                        Toast.makeText(this, "TE QUEDAN DOS VIDAS", Toast.LENGTH_SHORT).show()
                        imgVidas.setImageResource(R.drawable.dos)
                    }
                    0 -> {
                        Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
                        numVidas = 3
                        puntuacionCalculo -= 10
                        if (puntuacionCalculo < 0) {
                            puntuacionCalculo = 0
                        }
                        usuario.setPuntuacionCalculo(puntuacionCalculo)
                        usuario.setVidas(numVidas)
                        usuarioDAO.actualizar(usuario)
                        mp.stop()
                        mp.release()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                usuario.setVidas(numVidas)
                usuarioDAO.actualizar(usuario)
            }
            handler.postDelayed({
                txtPuntuacion.text = puntuacionCalculo.toString()
                txtEvaluar.setText("")
            }, 1000) // 1 segundo de retraso
            usuario.setPuntuacionCalculo(puntuacionCalculo)
            usuarioDAO.actualizar(usuario)
            aleatorio()
        } else {
            Toast.makeText(this, "ESCRIBE UNA RESPUESTA", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Método utilizado para colocar los datos del jugador en la interfaz.
     *
     * Este método actualiza los elementos de la interfaz de usuario con los datos del jugador, como el nombre y la puntuación.
     *
     * @return No devuelve ningún valor.
     */
    fun colocarDatos() {
        txtJugador.text = nombre
        txtPuntuacion.text = puntuacionCalculo.toString()
        when (numVidas) {
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
        mp.stop()
        mp.release()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}