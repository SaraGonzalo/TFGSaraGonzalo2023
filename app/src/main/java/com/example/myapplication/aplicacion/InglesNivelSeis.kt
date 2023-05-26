package com.example.myapplication.aplicacion
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
/**
 * Clase que representa el nivel seis del juego de inglés.
 * Extiende de la clase abstracta JuegoIngles.
 * Proporciona funcionalidad específica para el nivel seis, relacionado con los números, instrumentos, vehículos, países, comida... en inglés.
 */
class InglesNivelSeis : JuegoIngles() {
    private lateinit var numeros: Array<String>
    private lateinit var numbers: Array<String>
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
        Toast.makeText(
            this,
            "LEVEL 6: NUMBERS, INSTRUMENTS, VEHICLES, COUNTRIES, FOOD...",
            Toast.LENGTH_SHORT
        ).show()
        numeros = arrayOf(
            "cero", "uno", "numdos", "numtres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete",
            "dieciocho", "diecinueve", "veinte", "veintiuno", "veintidos", "veintitres", "veinticuatro",
            "veinticinco", "veintiseis", "veintisiete", "veintiocho", "accordion", "castanets", "drum",
            "drums", "guitar", "harp", "maracas", "piano", "recorder", "saxophone", "tambourine",
            "trumpet", "violin", "xylophone", "bicycle", "boat", "bus", "camper", "canoe", "car",
            "crane", "helicopter", "hotairballoon", "lorry", "motorbike", "plane", "tractor", "train",
            "tram", "underground", "underwater", "van", "albania", "argelia", "argentina", "australia",
            "austria", "belgica", "bosnia y herzegovina", "brasil", "dinamarca", "ecuador", "egipto",
            "estonia", "finlandia", "francia", "georgia", "alemania", "ghana", "grecia", "honduras",
            "hungria", "islandia", "india", "irlanda", "italia", "jamaica", "japon", "letonia", "lituania",
            "mexico", "marruecos", "paisesbajos", "coreadelnorte", "panama", "paraguay", "peru", "polonia",
            "portugal", "rumania", "rusia", "arabiasaudi", "singapur", "eslovaquia", "sudafrica",
            "coreadelsur", "espana", "suecia", "suiza", "tailandia", "tunez", "ucrania", "reinounido",
            "estadosunidos", "uruguay", "gales", "apple", "asparagus", "aubergine", "banana", "blueberry",
            "bread", "carrot", "cheese", "cherry", "chocolate", "corn", "egg", "garlic",
            "grape", "hamburguer", "kiwi", "lemon", "melon", "milk", "mushroom", "onion", "orange", "pear",
            "pepper", "pineapple", "pizza", "potato", "raspberry", "rice", "strawberry", "tomato", "water",
            "watermelon","bee","cat","cow","dog","elephant","frog","giraffe","horse","lion","monkey","rabbit","spider","turtle"
        )
        numbers = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
            "nineteen", "twenty", "twenty one", "twenty two", "twenty three", "twenty four",
            "twenty five", "twenty six", "twenty seven", "twenty eight", "accordion", "castanets",
            "drum", "drums", "guitar", "harp", "maracas", "piano", "recorder", "saxophone", "tambourine",
            "trumpet", "violin", "xylophone", "bicycle", "boat", "bus", "camper", "canoe", "car", "crane",
            "helicopter", "hot air balloon", "lorry", "motorbike", "plane", "tractor", "train", "tram",
            "underground", "underwater", "van", "albania", "algeria", "argentina", "australia", "austria",
            "belgium", "bosnia and herzegovina", "brazil", "denmark", "ecuador", "egypt", "estonia",
            "finland", "france", "georgia", "germany", "ghana", "greece", "honduras", "hungary", "iceland",
            "india", "ireland", "italy", "jamaica", "japan", "latvia", "lithuania", "mexico", "morocco",
            "netherlands", "north korea", "panama", "paraguay", "peru", "poland", "portugal", "romania",
            "russia", "saudi arabia", "singapore", "slovakia", "south africa", "south korea", "spain",
            "sweden", "switzerland", "thailand", "tunisia", "ukraine", "united kingdom", "united states",
            "uruguay", "welsh", "apple", "asparagus", "aubergine", "banana", "blueberry", "bread", "carrot",
            "cheese", "cherry", "chocolate", "corn", "egg", "garlic", "grape", "hamburguer",
            "kiwi", "lemon", "melon", "milk", "mushroom", "onion", "orange", "pear", "pepper", "pineapple",
            "pizza", "potato", "raspberry", "rice", "strawberry", "tomato", "water", "watermelon", "bee",
            "cat","cow","dog","elephant","frog","giraffe","horse","lion","monkey","rabbit","spider","turtle"
        )
        traduccion = arrayOf(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
            "nineteen", "twenty", "twentyone", "twentytwo", "twentythree", "twentyfour", "twentyfive",
            "twentysix", "twentyseven", "twentyeight", "accordion", "castanets", "drum", "drums", "guitar",
            "harp", "maracas", "piano", "recorder", "saxophone", "tambourine", "trumpet", "violin", "xylophone",
            "bicycle", "boat", "bus", "camper", "canoe", "car", "crane", "helicopter", "hotairballoon",
            "lorry", "motorbike", "plane", "tractor", "train", "tram", "underground", "underwater", "van",
            "albania", "algeria", "argentina", "australia", "austria", "belgium", "bosniaandherzegovina",
            "brazil", "denmark", "ecuador", "egypt", "estonia", "finland", "france", "georgia", "germany",
            "ghana", "greece", "honduras", "hungary", "iceland", "india", "ireland", "italy", "jamaica",
            "japan", "latvia", "lithuania", "mexico", "morocco", "netherlands", "northkorea", "panama",
            "paraguay", "peru", "poland", "portugal", "romania", "russia", "saudiarabia", "singapore",
            "slovakia", "southafrica", "southkorea", "spain", "sweden", "switzerland", "thailand", "tunisia",
            "ukraine", "unitedkingdom", "unitedstates", "uruguay", "welsh", "apple", "asparagus", "aubergine",
            "banana", "blueberry", "bread", "carrot", "cheese", "cherry", "chocolate", "corn",
            "egg", "garlic", "grape", "hamburguer", "kiwi", "lemon", "melon", "milk", "mushroom", "onion",
            "orange", "pear", "pepper", "pineapple", "pizza", "potato", "raspberry", "rice", "strawberry",
            "tomato", "water", "watermelon","bee","cat","cow","dog","elephant","frog","giraffe","horse","lion","monkey","rabbit","spider","turtle"
        )
        aleatorio()
    }
    /**
     * Método para generar un número aleatorio y mostrar la imagen y palabra correspondientes.
     * Si la puntuación de inglés es <2000000, siguen saliendo imágenes aleatorias
     * @return No devuelve ningún valor.
     */
    override fun aleatorio() {
        val numeroUno: Int
        val id1: Int
        if (puntuacionIngles < 2000000) {
            numeroUno = (Math.random() * 148).toInt()
            resultado = numbers[numeroUno]
            palabra = traduccion[numeroUno]
            id1 = resources.getIdentifier(numeros[numeroUno], "drawable", packageName)
            imgIngles.setImageResource(id1)
        } else {
            Toast.makeText(this, "YOU BEAT THE GAME", Toast.LENGTH_SHORT).show()
            mp.stop()
            mp.release()
            val intent = Intent(this, MainActivity::class.java)
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