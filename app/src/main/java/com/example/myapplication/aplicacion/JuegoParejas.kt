package com.example.myapplication.aplicacion

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.modelodatos.Usuario
import com.example.myapplication.persistencia.UsuarioDAO
/**
 * Actividad del juego parejas
 */
class JuegoParejas : AppCompatActivity(), View.OnClickListener {
    //variables declaradas con var puede variar su valor a lo largo del código
    //variables declaradas con val no varía su valor a lo largo del código
    //lateinit->declarar una variable que se inicializará más adelante antes de acceder a ella por primera vez.
    private lateinit var imagenes: IntArray
    private lateinit var botones: MutableList<MiBoton>
    private var todosDeshabilitados: Boolean = false
    private lateinit var panel: LinearLayout
    private var contador: Int = 0
    private lateinit var aux: MiBoton
    private lateinit var btTemporal: MiBoton
    private lateinit var txtJugador: TextView
    private lateinit var txtPuntuacion: TextView
    private var nombre: String = ""
    private var contrasena: String = ""
    private var puntuacionCalculo: Int = 0
    private var puntuacionBandera: Int = 0
    private var puntuacionIngles: Int = 0
    private var puntuacionParejas: Int = 0
    private var numVidas: Int = 0
    private lateinit var usuario: Usuario
    private lateinit var mp: MediaPlayer
    private lateinit var correcto: MediaPlayer
    private lateinit var incorrecto: MediaPlayer
    private var numero: Int = 0
    private var numFilas: Int = 0
    private var numColumnas: Int = 0
    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, en caso de que haya sido previamente destruida y vuelva a crearse
     * @return No devuelve ningún valor.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parejas)
        Toast.makeText(this, "ENCUENTRA LAS PAREJAS", Toast.LENGTH_SHORT).show()
        // Configuración de la barra de acción
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.parejas)))
        // Obtener referencias a los elementos de la interfaz de usuario
        txtJugador = findViewById(R.id.txtJugador)
        txtPuntuacion = findViewById(R.id.txtPuntuacion)

        //datos que vienen de la actividad anterior
        nombre = intent.getStringExtra("jugador") ?: ""
        contrasena = intent.getStringExtra("contrasena") ?: ""
        puntuacionCalculo = intent.getStringExtra("puntuacionCalculo")?.toInt() ?: 0
        puntuacionIngles = intent.getStringExtra("puntuacionIngles")?.toInt() ?: 0
        puntuacionBandera = intent.getStringExtra("puntuacionBandera")?.toInt() ?: 0
        puntuacionParejas = intent.getStringExtra("puntuacionParejas")?.toInt() ?: 0
        numVidas = intent.getStringExtra("numVidas")?.toInt() ?: 0
        usuario = Usuario(nombre, contrasena, puntuacionCalculo, puntuacionIngles, puntuacionBandera, puntuacionParejas, numVidas)
        // Configuración del nivel del juego
        nivel()
        // Rellenar el array de imágenes aleatorias
        rellenarArray()
        // Obtener referencia al panel donde se mostrarán los botones
        panel = findViewById(R.id.panel)
        // Rellenar el panel con los botones
        rellenarPanel()
        // Configuración del audio
        mp = MediaPlayer.create(this, R.raw.pareja)
        mp.start()
        mp.isLooping = true
        colocarDatos()

        correcto = MediaPlayer.create(this, R.raw.acierto)
        incorrecto = MediaPlayer.create(this, R.raw.error)
    }
    /**
     * Rellena el array de botones para el juego de parejas.
     * @return No devuelve ningún valor.
     */
    fun rellenarArray() { // Método para rellenar el array de botones
        val botonesAuxiliares = generarImagenesAleatorias() // Se genera un array de imágenes aleatorias
        //necesario porque hay un vector de imágenes con todas las imágenes que hay en ese nivel
        botonesAuxiliares.shuffle() // Se barajan las imágenes aleatoriamente
        botones = ArrayList() // Se crea un nuevo ArrayList para almacenar los botones
        // Se itera desde 0 hasta el valor de la variable "numero"-1
        for (i in 0 until numero) {
            botones.add(MiBoton(this, botonesAuxiliares[i].getImagen()))
            botones.add(MiBoton(this, botonesAuxiliares[i].getImagen()))
        }
    }
    /**
     * Rellena el panel con los botones para el juego de parejas.
     * @return No devuelve ningún valor.
     */
    fun rellenarPanel() {// Método para rellenar el panel con los botones
        // Eliminar el panel actual si existe
        if (panel.childCount > 0) {
            panel.removeAllViews()
        }
        //necesario porque el array de botones no está barajado, luego salen los pares juntos
        botones.shuffle()
        val gridLayout = GridLayout(this) // Crear un nuevo GridLayout
        gridLayout.columnCount = numColumnas // Establecer el número de columnas del GridLayout
        gridLayout.rowCount = numFilas // Establecer el número de filas del GridLayout
        gridLayout.useDefaultMargins = true // Utilizar márgenes predeterminados en el GridLayout
        panel.gravity = Gravity.CENTER // Establecer la gravedad del panel para centrar su contenido
        panel.addView(gridLayout) // Agregar el GridLayout al panel
        for (i in 0 until numero * 2) {
            gridLayout.addView(botones[i]) // Agregar el botón al GridLayout
            botones[i].setOnClickListener(this) // Establecer el OnClickListener para el botón
        }
    }
    /**
     * Manejador del evento onClick para los botones del juego de memoria de parejas.
     *
     * @param v La vista del botón en el que se hizo clic.
     *
     * @return No devuelve ningún valor.
     */
    override fun onClick(v: View) { // Manejador del evento onClick
        val tamano = botones.size
        val usuarioDAO = UsuarioDAO(this)
        btTemporal = v as MiBoton // Obtener el botón que disparó el evento como MiBoton
        btTemporal.text = "" // Cambiar el texto del botón
        btTemporal.setBackgroundResource(btTemporal.getImagen()) // Establecer el fondo del botón con la imagen
        btTemporal.isEnabled = false // Deshabilitar el botón temporalmente
        if (contador == 0) {
            aux = btTemporal
            contador++
        } else {
            //bloquear toda interacción con la pantalla
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            contador = 0
            // Retrasar la ejecución del bloque de código por 1 segundo utilizando un Handler
            btTemporal.postDelayed({
                btTemporal.isEnabled = false
                if (aux.getImagen() != btTemporal.getImagen()) {
                    aux.text = "?"
                    aux.background = ColorDrawable(Color.rgb(90, 204, 31))
                    aux.isEnabled = true
                    btTemporal.text = "?"
                    btTemporal.background = ColorDrawable(Color.rgb(90, 204, 31))
                    btTemporal.isEnabled = true
                    incorrecto.start()
                } else {
                    correcto.start()
                }
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)//vuelve a habilitar la interacción con la pantalla
            }, 1000)
            todosDeshabilitados = true
            var i = 0
            while (i < tamano && todosDeshabilitados) {
                if (botones[i].isEnabled) {
                    todosDeshabilitados = false
                }
                i++
            }
            if (todosDeshabilitados) {
                puntuacionParejas = puntuacionParejas + 10
                txtPuntuacion.text = puntuacionParejas.toString()
                usuario.setPuntuacionParejas(puntuacionParejas)
                usuarioDAO.actualizar(usuario)
                resetearPanel()
            }
        }
    }
    /**
     * Método utilizado para colocar los datos del jugador en la interfaz.
     *
     * Este método actualiza los elementos de la interfaz de usuario con los datos del jugador, como el nombre y la puntuación.
     *
     * @return No devuelve ningún valor.
     */
    fun colocarDatos() { // Método para colocar los datos del jugador en la interfaz
        txtJugador.text = nombre
        txtPuntuacion.text = puntuacionParejas.toString()
    }
    /**
     * Manejador del evento onBackPressed.
     *
     * Este método se ejecuta cuando se presiona el botón de retroceso en el dispositivo.
     * Inicia una nueva actividad MainActivity utilizando un intent para volver a la pantalla principal.
     *
     * @return No devuelve ningún valor.
     */
    override fun onBackPressed() { // Manejador del evento onBackPressed
        mp.stop()
        mp.release()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    /**
     * Método utilizado para generar imágenes aleatorias.
     *
     * Este método crea una lista de objetos de tipo MiBoton que representan las imágenes del juego.
     * Las imágenes se generan aleatoriamente a partir de una lista predefinida de imágenes.
     *
     * @return Una ArrayList de objetos de tipo MiBoton que representan las imágenes generadas aleatoriamente.
     */
    fun generarImagenesAleatorias(): ArrayList<MiBoton> { // Método para generar imágenes
        val botones = ArrayList<MiBoton>()
        val tamano = imagenes.size
        for (i in 0 until tamano) {
            botones.add(MiBoton(this, imagenes[i]))
        }
        return botones
    }
    /**
     * Método utilizado para determinar el nivel del juego según la puntuación del jugador.
     *
     * Este método establece el nivel del juego en función de la puntuación obtenida por el jugador.
     * Muestra un mensaje Toast con el nombre del nivel y ajusta los parámetros del juego, como el número de imágenes,
     * el número de filas y columnas, y rellena el vector de nivel correspondiente.
     *
     * @return No devuelve ningún valor.
     */
    fun nivel() { // Método para determinar el nivel del juego según la puntuación
        when {
            puntuacionParejas < 10 -> {
                Toast.makeText(this, "NIVEL 1: ENCUENTRA PAREJAS DE VEHÍCULOS", Toast.LENGTH_SHORT).show()
                numero = 3
                numColumnas = 3
                numFilas = 2
                rellenarVectorNivelUno()
            }
            puntuacionParejas < 20 -> {
                Toast.makeText(this, "NIVEL 2: ENCUENTRA PAREJAS DE ANIMALES", Toast.LENGTH_SHORT).show()
                numero = 4
                numFilas = 2
                numColumnas = 4
                rellenarVectorNivelDos()
            }
            puntuacionParejas < 30 -> {
                Toast.makeText(this, "NIVEL 3: ENCUENTRA LAS PAREJAS", Toast.LENGTH_SHORT).show()
                numero = 5
                numFilas = 2
                numColumnas = 5
                rellenarVectorNivelTres()
            }
            puntuacionParejas < 40 -> {
                Toast.makeText(this, "NIVEL 4: ENCUENTRA PAREJAS DE COMIDA", Toast.LENGTH_SHORT).show()
                rellenarVectorNivelCuatro()
                numero = 6
                numFilas = 3
                numColumnas = 4
            }
            puntuacionParejas < 50 -> {
                Toast.makeText(this, "NIVEL 5: ENCUENTRA PAREJAS DE BANDERAS", Toast.LENGTH_SHORT).show()
                rellenarVectorNivelCinco()
                numero = 8
                numFilas = 4
                numColumnas = 4
            }
            else -> {
                Toast.makeText(this, "¡HAS ALCANZADO EL ÚLTIMO NIVEL! ¡SIGUE JUGANDO PARA MEJORAR TU PUNTUACIÓN!", Toast.LENGTH_SHORT).show()
                numero = 10
                numColumnas = 4
                numFilas = 5
                rellenarVectorNivelSeis()
            }
        }
    }
    /**
     * Método utilizado para reiniciar el panel y generar nuevos botones.
     *
     * Este método reinicia el panel del juego y genera nuevos botones para jugar.
     * Deshabilita todos los botones, establece el texto de cada botón a "?" y restablece el fondo de cada botón.
     * Luego, se llama al método `nivel()` para determinar el nuevo nivel del juego.
     * A continuación, se rellena el array de botones y se rellena el panel con los nuevos botones generados.
     *
     * @return No devuelve ningún valor.
     */
    fun resetearPanel() { // Método para reiniciar el panel y generar nuevos botones
        nivel()
        rellenarArray()
        rellenarPanel()
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 1.
     *
     * Este método asigna las imágenes correspondientes al nivel 1 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelUno() { // Método para rellenar el vector de imágenes del nivel 1
        imagenes = intArrayOf(R.drawable.bicycle,R.drawable.boat,R.drawable.bus,R.drawable.camper,R.drawable.canoe,
            R.drawable.car,R.drawable.crane,R.drawable.helicopter,R.drawable.hotairballoon,R.drawable.lorry,R.drawable.motorbike,
            R.drawable.plane,R.drawable.tractor,R.drawable.train,R.drawable.tram,R.drawable.underground,R.drawable.underwater,
            R.drawable.van)
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 2.
     *
     * Este método asigna las imágenes correspondientes al nivel 2 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelDos() { // Método para rellenar el vector de imágenes del nivel 2
        imagenes = intArrayOf(R.drawable.animal1,R.drawable.animal2,R.drawable.animal3,R.drawable.animal4,R.drawable.animal5,
            R.drawable.animal6,R.drawable.animal7,R.drawable.animal8,R.drawable.animal9,R.drawable.animal10,R.drawable.animal11,
            R.drawable.animal12,R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,
            R.drawable.animal18,R.drawable.animal19,R.drawable.animal20,R.drawable.animal21,R.drawable.animal22,R.drawable.animal23,R.drawable.animal24,
            R.drawable.animal25,R.drawable.animal26,R.drawable.animal27,R.drawable.animal28,R.drawable.animal29,R.drawable.animal30,
            R.drawable.animal31,R.drawable.animal32,R.drawable.animal33,R.drawable.animal34,R.drawable.animal35,R.drawable.animal36,
            R.drawable.animal37,R.drawable.animal38,R.drawable.animal39,R.drawable.animal40,R.drawable.animal41,R.drawable.animal42,
            R.drawable.animal43,R.drawable.animal44,R.drawable.animal45,R.drawable.animal46,R.drawable.animal47,R.drawable.animal48,
            R.drawable.animal49,R.drawable.animal50,R.drawable.animal51,R.drawable.animal52,R.drawable.animal53,R.drawable.animal54,
            R.drawable.animal55,R.drawable.animal56,R.drawable.animal57,R.drawable.animal58,R.drawable.animal59,R.drawable.animal60,R.drawable.animal61,
            R.drawable.animal62,R.drawable.animal63,R.drawable.bee,R.drawable.cat,R.drawable.cow,R.drawable.dog,R.drawable.elephant,
            R.drawable.frog,R.drawable.giraffe,R.drawable.horse,R.drawable.lion,R.drawable.monkey,R.drawable.rabbit,R.drawable.spider,R.drawable.turtle)
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 3.
     *
     * Este método asigna las imágenes correspondientes al nivel 3 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelTres() { // Método para rellenar el vector de imágenes del nivel 3
        imagenes = intArrayOf(R.drawable.pelota1,R.drawable.pelota2,R.drawable.pelota3,R.drawable.pelota4,R.drawable.pelota5,R.drawable.pelota6,
            R.drawable.pelota7,R.drawable.pelota8,R.drawable.pelota9,R.drawable.pelota10,R.drawable.pelota11,R.drawable.pelota12,R.drawable.pelota13,
            R.drawable.pelota14,R.drawable.pelota15,R.drawable.pelota16,R.drawable.pelota17,R.drawable.pelota18,R.drawable.pelota19,R.drawable.pelota20,
            R.drawable.pelota21,R.drawable.pelota22,R.drawable.pelota23,R.drawable.pelota24,R.drawable.pelota25,R.drawable.pelota26,R.drawable.pelota27,
            R.drawable.pelota28,R.drawable.pelota29,R.drawable.pelota30,R.drawable.pelota31,R.drawable.pelota32,R.drawable.pelota33,R.drawable.pelota34,
            R.drawable.pelota35,R.drawable.pelota36,R.drawable.pelota37,R.drawable.pelota38,R.drawable.pelota39,R.drawable.pelota40,R.drawable.pelota41,
            R.drawable.pelota42,R.drawable.pelota43,R.drawable.pelota44,R.drawable.pelota45,R.drawable.pelota46,R.drawable.pelota47,R.drawable.pelota48,
            R.drawable.pelota49,R.drawable.pelota50,R.drawable.pelota51,R.drawable.pelota52,R.drawable.pelota53)
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 4.
     *
     * Este método asigna las imágenes correspondientes al nivel 4 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelCuatro() { // Método para rellenar el vector de imágenes del nivel 4
        imagenes = intArrayOf(R.drawable.apple,R.drawable.asparagus,R.drawable.aubergine,R.drawable.banana,R.drawable.blueberry,
        R.drawable.bread,R.drawable.carrot,R.drawable.cheese,R.drawable.cherry,R.drawable.chocolate,R.drawable.comida1,
        R.drawable.comida1,R.drawable.comida2,R.drawable.comida3,R.drawable.comida4,R.drawable.comida5,R.drawable.comida6,
        R.drawable.comida7,R.drawable.comida8,R.drawable.comida9,R.drawable.comida10,R.drawable.comida11,R.drawable.comida12,
        R.drawable.comida13,R.drawable.comida14,R.drawable.comida15,R.drawable.comida16,R.drawable.comida17,R.drawable.comida18,
        R.drawable.comida19,R.drawable.comida20,R.drawable.comida21,R.drawable.comida22,R.drawable.comida23,R.drawable.comida24,
        R.drawable.comida25,R.drawable.comida26,R.drawable.comida27,R.drawable.comida28,R.drawable.comida29,R.drawable.comida30,
        R.drawable.comida31,R.drawable.comida32,R.drawable.comida33,R.drawable.comida34,R.drawable.comida35,R.drawable.comida36,R.drawable.comida37,
        R.drawable.comida38,R.drawable.comida39,R.drawable.comida40,R.drawable.comida41,R.drawable.comida42,R.drawable.comida43,
        R.drawable.comida44,R.drawable.comida45,R.drawable.comida46,R.drawable.comida47,R.drawable.comida48,R.drawable.comida49,
        R.drawable.comida50,R.drawable.comida51,R.drawable.comida52,R.drawable.comida53,R.drawable.comida54,R.drawable.comida55,
        R.drawable.comida56,R.drawable.comida57,R.drawable.comida58,R.drawable.comida59,R.drawable.comida60,R.drawable.comida61,
        R.drawable.comida62,R.drawable.comida63,R.drawable.comida64,R.drawable.comida65,R.drawable.comida67,R.drawable.comida68,
        R.drawable.corn,R.drawable.egg,R.drawable.garlic,R.drawable.grape,R.drawable.hamburguer,
        R.drawable.kiwi,R.drawable.lemon,R.drawable.melon,R.drawable.milk,R.drawable.mushroom,R.drawable.onion,R.drawable.orange,
        R.drawable.pear,R.drawable.pepper,R.drawable.pineapple,R.drawable.pizza,R.drawable.potato,R.drawable.raspberry,R.drawable.rice,
        R.drawable.strawberry,R.drawable.tomato,R.drawable.water,R.drawable.watermelon)
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 5.
     *
     * Este método asigna las imágenes correspondientes al nivel 5 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelCinco() { // Método para rellenar el vector de imágenes del nivel 5
        imagenes = intArrayOf(R.drawable.alemania,R.drawable.argelia,R.drawable.argentina,R.drawable.australia,R.drawable.austria,
            R.drawable.belgica,R.drawable.brasil,R.drawable.canada,R.drawable.chile,R.drawable.china,R.drawable.colombia,
            R.drawable.coreadelsur,R.drawable.cuba,R.drawable.dinamarca,R.drawable.espana,R.drawable.estadosunidos,R.drawable.estonia,
            R.drawable.finlandia,R.drawable.francia,R.drawable.grecia,R.drawable.honduras,R.drawable.hungria,R.drawable.irlanda,
            R.drawable.islandia,R.drawable.italia,R.drawable.jamaica,R.drawable.japon,R.drawable.letonia,R.drawable.lituania,
            R.drawable.marruecos,R.drawable.paisesbajos,R.drawable.polonia,R.drawable.reinounido,R.drawable.republicacheca,R.drawable.rumania,
            R.drawable.rusia,R.drawable.singapur,R.drawable.sudafrica,R.drawable.suecia,R.drawable.suiza,R.drawable.tailandia,R.drawable.turquia,
            R.drawable.ucrania,R.drawable.uruguay)
    }
    /**
     * Método utilizado para rellenar el vector de imágenes del nivel 6.
     *
     * Este método asigna las imágenes correspondientes al nivel 6 al vector `imagenes`.
     * Las imágenes se representan mediante identificadores de recursos drawable.
     *
     * @return No devuelve ningún valor.
     */
    fun rellenarVectorNivelSeis() { // Método para rellenar el vector de imágenes del nivel 6
        imagenes= intArrayOf(R.drawable.bicycle,R.drawable.boat,R.drawable.bus,R.drawable.camper,R.drawable.canoe,
            R.drawable.car,R.drawable.crane,R.drawable.helicopter,R.drawable.hotairballoon,R.drawable.lorry,
            R.drawable.motorbike,R.drawable.plane,R.drawable.tractor,R.drawable.train,R.drawable.tram,R.drawable.underground,
            R.drawable.underwater,R.drawable.van,R.drawable.animal1,R.drawable.animal2,R.drawable.animal3,R.drawable.animal4,R.drawable.animal5,
            R.drawable.animal6,R.drawable.animal7,R.drawable.animal8,R.drawable.animal9,R.drawable.animal10,R.drawable.animal11,
            R.drawable.animal12,R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,
            R.drawable.animal18,R.drawable.animal19,R.drawable.animal20,R.drawable.animal21,R.drawable.animal22,R.drawable.animal23,R.drawable.animal24,
            R.drawable.animal25,R.drawable.animal26,R.drawable.animal27,R.drawable.animal28,R.drawable.animal29,R.drawable.animal30,
            R.drawable.animal31,R.drawable.animal32,R.drawable.animal33,R.drawable.animal34,R.drawable.animal35,R.drawable.animal36,
            R.drawable.animal37,R.drawable.animal38,R.drawable.animal39,R.drawable.animal40,R.drawable.animal41,R.drawable.animal42,
            R.drawable.animal43,R.drawable.animal44,R.drawable.animal45,R.drawable.animal46,R.drawable.animal47,R.drawable.animal48,
            R.drawable.animal49,R.drawable.animal50,R.drawable.animal51,R.drawable.animal52,R.drawable.animal53,R.drawable.animal54,
            R.drawable.animal55,R.drawable.animal56,R.drawable.animal57,R.drawable.animal58,R.drawable.animal59,R.drawable.animal60,R.drawable.animal61,
            R.drawable.animal62,R.drawable.animal63,R.drawable.bee,R.drawable.cat,R.drawable.cow,R.drawable.dog,R.drawable.elephant,
            R.drawable.frog,R.drawable.giraffe,R.drawable.horse,R.drawable.lion,R.drawable.monkey,R.drawable.rabbit,R.drawable.spider,R.drawable.turtle,R.drawable.pelota1,R.drawable.pelota2,R.drawable.pelota3,R.drawable.pelota4,R.drawable.pelota5,R.drawable.pelota6,
            R.drawable.pelota7,R.drawable.pelota8,R.drawable.pelota9,R.drawable.pelota10,R.drawable.pelota11,R.drawable.pelota12,R.drawable.pelota13,
            R.drawable.pelota14,R.drawable.pelota15,R.drawable.pelota16,R.drawable.pelota17,R.drawable.pelota18,R.drawable.pelota19,R.drawable.pelota20,
            R.drawable.pelota21,R.drawable.pelota22,R.drawable.pelota23,R.drawable.pelota24,R.drawable.pelota25,R.drawable.pelota26,R.drawable.pelota27,
            R.drawable.pelota28,R.drawable.pelota29,R.drawable.pelota30,R.drawable.pelota31,R.drawable.pelota32,R.drawable.pelota33,R.drawable.pelota34,
            R.drawable.pelota35,R.drawable.pelota36,R.drawable.pelota37,R.drawable.pelota38,R.drawable.pelota39,R.drawable.pelota40,R.drawable.pelota41,
            R.drawable.pelota42,R.drawable.pelota43,R.drawable.pelota44,R.drawable.pelota45,R.drawable.pelota46,R.drawable.pelota47,R.drawable.pelota48,
            R.drawable.pelota49,R.drawable.pelota50,R.drawable.pelota51,R.drawable.pelota52,R.drawable.pelota53,R.drawable.apple,R.drawable.asparagus,R.drawable.aubergine,R.drawable.banana,R.drawable.blueberry,
            R.drawable.bread,R.drawable.carrot,R.drawable.cheese,R.drawable.cherry,R.drawable.chocolate,R.drawable.comida1,
            R.drawable.comida1,R.drawable.comida2,R.drawable.comida3,R.drawable.comida4,R.drawable.comida5,R.drawable.comida6,
            R.drawable.comida7,R.drawable.comida8,R.drawable.comida9,R.drawable.comida10,R.drawable.comida11,R.drawable.comida12,
            R.drawable.comida13,R.drawable.comida14,R.drawable.comida15,R.drawable.comida16,R.drawable.comida17,R.drawable.comida18,
            R.drawable.comida19,R.drawable.comida20,R.drawable.comida21,R.drawable.comida22,R.drawable.comida23,R.drawable.comida24,
            R.drawable.comida25,R.drawable.comida26,R.drawable.comida27,R.drawable.comida28,R.drawable.comida29,R.drawable.comida30,
            R.drawable.comida31,R.drawable.comida32,R.drawable.comida33,R.drawable.comida34,R.drawable.comida35,R.drawable.comida36,R.drawable.comida37,
            R.drawable.comida38,R.drawable.comida39,R.drawable.comida40,R.drawable.comida41,R.drawable.comida42,R.drawable.comida43,
            R.drawable.comida44,R.drawable.comida45,R.drawable.comida46,R.drawable.comida47,R.drawable.comida48,R.drawable.comida49,
            R.drawable.comida50,R.drawable.comida51,R.drawable.comida52,R.drawable.comida53,R.drawable.comida54,R.drawable.comida55,
            R.drawable.comida56,R.drawable.comida57,R.drawable.comida58,R.drawable.comida59,R.drawable.comida60,R.drawable.comida61,
            R.drawable.comida62,R.drawable.comida63,R.drawable.comida64,R.drawable.comida65,R.drawable.comida67,R.drawable.comida68,
            R.drawable.corn,R.drawable.egg,R.drawable.garlic,R.drawable.grape,R.drawable.hamburguer,
            R.drawable.kiwi,R.drawable.lemon,R.drawable.melon,R.drawable.milk,R.drawable.mushroom,R.drawable.onion,R.drawable.orange,
            R.drawable.pear,R.drawable.pepper,R.drawable.pineapple,R.drawable.pizza,R.drawable.potato,R.drawable.raspberry,R.drawable.rice,
            R.drawable.strawberry,R.drawable.tomato,R.drawable.water,R.drawable.watermelon,R.drawable.alemania,R.drawable.argelia,R.drawable.argentina,R.drawable.australia,R.drawable.austria,
            R.drawable.belgica,R.drawable.brasil,R.drawable.canada,R.drawable.chile,R.drawable.china,R.drawable.colombia,
            R.drawable.coreadelsur,R.drawable.cuba,R.drawable.dinamarca,R.drawable.espana,R.drawable.estadosunidos,R.drawable.estonia,
            R.drawable.finlandia,R.drawable.francia,R.drawable.grecia,R.drawable.honduras,R.drawable.hungria,R.drawable.irlanda,
            R.drawable.islandia,R.drawable.italia,R.drawable.jamaica,R.drawable.japon,R.drawable.letonia,R.drawable.lituania,
            R.drawable.marruecos,R.drawable.paisesbajos,R.drawable.polonia,R.drawable.reinounido,R.drawable.republicacheca,R.drawable.rumania,
            R.drawable.rusia,R.drawable.singapur,R.drawable.sudafrica,R.drawable.suecia,R.drawable.suiza,R.drawable.tailandia,R.drawable.turquia,
            R.drawable.ucrania,R.drawable.uruguay)
    }
}