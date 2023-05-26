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
 * Clase abstracta base para el juego de banderas.
 * Extiende de AppCompatActivity.
 * Proporciona funcionalidad común y métodos abstractos para los juegos de banderas.
 */
abstract class JuegoBandera : AppCompatActivity() {
    private lateinit var txtJugador: TextView
    private lateinit var txtPuntuacion: TextView
    protected lateinit var imgVidas: ImageView
    protected lateinit var imgBandera: ImageView
    private lateinit var txtRespuesta: EditText
    protected lateinit var nombre: String
    protected lateinit var contrasena: String
    protected var puntuacionCalculo = 0
    protected var puntuacionBandera = 0
    protected var puntuacionIngles = 0
    protected var puntuacionParejas = 0
    protected var numVidas = 0
    private lateinit var usuario: Usuario
    protected lateinit var mp: MediaPlayer
    private lateinit var correcto: MediaPlayer
    private lateinit var incorrecto: MediaPlayer
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bandera)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.banderas)))

        txtJugador = findViewById(R.id.txtJugador)
        txtPuntuacion = findViewById(R.id.txtPuntuacion)
        imgVidas = findViewById(R.id.imgVidas)
        imgBandera = findViewById(R.id.imgBandera)
        txtRespuesta = findViewById(R.id.txtRespuesta)

        // Datos que vienen de la actividad anterior
        nombre = intent.getStringExtra("jugador") ?: ""
        contrasena = intent.getStringExtra("contrasena") ?: ""
        puntuacionCalculo = intent.getStringExtra("puntuacionCalculo")?.toInt() ?: 0
        puntuacionIngles = intent.getStringExtra("puntuacionIngles")?.toInt() ?: 0
        puntuacionBandera = intent.getStringExtra("puntuacionBandera")?.toInt() ?: 0
        puntuacionParejas = intent.getStringExtra("puntuacionParejas")?.toInt() ?: 0
        numVidas = intent.getStringExtra("numVidas")?.toInt() ?: 0
        usuario = Usuario(nombre, contrasena, puntuacionCalculo, puntuacionIngles, puntuacionBandera, puntuacionParejas, numVidas)

        mp = MediaPlayer.create(this, R.raw.bandera)
        mp.start()
        mp.isLooping = true
        colocarDatos()

        correcto = MediaPlayer.create(this, R.raw.acierto)
        incorrecto = MediaPlayer.create(this, R.raw.error)
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
    fun comprobar(resultado: String) {
        val respuesta = txtRespuesta.text.toString()
        val handler = Handler(Looper.getMainLooper())
        val usuarioDAO = UsuarioDAO(this)
        if (respuesta.isNotEmpty()) {
            if (respuesta.equals(resultado, ignoreCase = true)) {
                // Audio de respuesta correcta
                correcto.start()
                puntuacionBandera++
            } else {
                // Audio respuesta incorrecta
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
                        puntuacionBandera -= 10
                        if (puntuacionBandera < 0) {
                            puntuacionBandera = 0
                        }
                        usuario.setPuntuacionBandera(puntuacionBandera)
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
                txtPuntuacion.text = puntuacionBandera.toString()
                txtRespuesta.setText("")
            }, 1000) // 1 segundo de retraso
            usuario.setPuntuacionBandera(puntuacionBandera)
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
        txtPuntuacion.text = puntuacionBandera.toString()
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