package com.example.myapplication.aplicacion
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myapplication.R
import com.example.myapplication.modelodatos.Usuario
import com.example.myapplication.persistencia.UsuarioDAO
/**
 * Actividad principal de la aplicación.
 */
class MainActivity : AppCompatActivity() {
    //variables declaradas con var puede variar su valor a lo largo del código
    //variables declaradas con val no varía su valor a lo largo del código
    //lateinit->declarar una variable que se inicializará más adelante antes de acceder a ella por primera vez.
    private lateinit var personaje: ImageView
    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var cambioImagen: CambioImagen
    private lateinit var mp: MediaPlayer
    private val handler = Handler(Looper.getMainLooper())
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamada al método onCreate de la clase base
        super.onCreate(savedInstanceState)
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_main)
        // Obtener una referencia a la Action Bar de la actividad
        val actionBar: ActionBar? = supportActionBar
        // Mostrar el icono del launcher en la Action Bar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
        // Obtener el color principal definido en los recursos
        val color: Int = resources.getColor(R.color.principal,null)
        // Establecer el color de fondo de la Action Bar
        actionBar?.setBackgroundDrawable(ColorDrawable(color))
        // Obtener referencias a las vistas de la actividad
        txtUsuario = findViewById(R.id.txtUsuario)
        txtContrasena = findViewById(R.id.txtContrasena)
        personaje = findViewById(R.id.personaje)
        // Crear un array de recursos de imágenes de personajes
        val personajes = intArrayOf(R.drawable.baloncesto,R.drawable.barco,
            R.drawable.ciencia,R.drawable.lapiz,R.drawable.libro)
        // Crear una instancia de la clase CambioImagen y pasar las referencias de la vista de personaje y el array de imágenes
        cambioImagen = CambioImagen(personaje, personajes, handler)
        cambioImagen.start()
        // Crear una instancia de MediaPlayer y cargar el archivo de audio "principal"
        mp = MediaPlayer.create(this, R.raw.principal)
        mp.start()
        mp.isLooping = true//se seguirá reproduciendo la pista de audio desde el principio una vez que se ha completado la reproducción
    }
    /**
     * Método que se llama al hacer clic en el botón "Jugar".
     *
     * @param view La vista del botón "Jugar".
     * @return No devuelve ningún valor.
     */
    fun jugar(view: View) {
        // Se verifica si los campos de texto no están vacíos
        if (txtContrasena.text.toString().isNotEmpty() && txtUsuario.text.toString().isNotEmpty()) {
            // Se crea un objeto de la clase Usuario con los valores ingresados en los campos de texto
            val usuario = Usuario(txtUsuario.text.toString(), txtContrasena.text.toString())
            val usuarioDAO = UsuarioDAO(this) // Se crea una instancia de UsuarioDAO
            // Se busca el usuario en la base de datos
            val usuarioEncontrado = usuarioDAO.buscar(usuario)
            // Se verifica si se encontró un usuario válido
            if (usuarioEncontrado != null) {
                // Intent para iniciar la actividad Minijuego
                val intent = Intent(this, Minijuego::class.java).apply {
                    putExtra("jugador", usuarioEncontrado.getNombre())
                    putExtra("contrasena", usuarioEncontrado.getContrasena())
                    putExtra("puntuacionCalculo", usuarioEncontrado.getPuntuacionCalculo().toString())
                    putExtra("puntuacionIngles", usuarioEncontrado.getPuntuacionIngles().toString())
                    putExtra("puntuacionBandera", usuarioEncontrado.getPuntuacionBandera().toString())
                    putExtra("puntuacionParejas", usuarioEncontrado.getPuntuacionParejas().toString())
                    putExtra("numVidas", usuarioEncontrado.getVidas().toString())
                }
                mp.stop()
                mp.release()
                startActivity(intent)
                // Se utiliza un Handler para programar una tarea que se ejecutará después de 1 segundo
                handler.postDelayed({
                    txtUsuario.setText("")
                    txtContrasena.setText("")
                }, 1000) // 1 segundo de retraso
            } else {
                Toast.makeText(this, "ERROR: USUARIO O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ERROR: ES NECESARIO RELLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Método que se llama al hacer clic en el botón "Registro".
     *
     * @param view La vista del botón "Registro".
     * @return No devuelve ningún valor.
     */
    fun registro(view: View) {
        //Se verifica si los campos no están vacíos
        if (txtContrasena.text.toString().isNotEmpty() && txtUsuario.text.toString().isNotEmpty()) {
            val usuarioDAO = UsuarioDAO(this)//instancia de UsuarioDAO
            val usuario = Usuario(txtUsuario.text.toString(), txtContrasena.text.toString())
            if (!usuarioDAO.insertar(usuario)) {
                Toast.makeText(this, "ERROR: NO SE HA PODIDO REALIZAR EL REGISTRO", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ERROR: ES NECESARIO RELLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
        }
    }
}