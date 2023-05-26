package com.example.myapplication.modelodatos
/**
 * Clase que representa un usuario.
 *
 * @property nombre Nombre del usuario.
 * @property contrasena Contraseña del usuario.
 * @property puntuacionCalculo Puntuación del usuario en el juego de cálculo.
 * @property puntuacionIngles Puntuación del usuario en el juego de inglés.
 * @property puntuacionBandera Puntuación del usuario en el juego de banderas.
 * @property puntuacionParejas Puntuación del usuario en el juego de parejas.
 * @property numVidas Número de vidas del usuario.
 *
 * @constructor Crea un usuario con todos los atributos.
 * @constructor Crea un usuario con el nombre y la contraseña, y el resto de atributos inicializados a cero o al valor por defecto.
 */
class Usuario {
    private var nombre: String
    private var contrasena: String
    private var puntuacionCalculo: Int
    private var puntuacionIngles: Int
    private var puntuacionBandera: Int
    private var puntuacionParejas: Int
    private var numVidas: Int

    /**
     * Crea un usuario con todos los atributos.
     *
     * @param nombre Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * @param puntuacionCalculo Puntuación del usuario en el juego de cálculo.
     * @param puntuacionIngles Puntuación del usuario en el juego de inglés.
     * @param puntuacionBandera Puntuación del usuario en el juego de banderas.
     * @param puntuacionParejas Puntuación del usuario en el juego de parejas.
     * @param numVidas Número de vidas del usuario.
     */
    constructor(nombre: String,contrasena: String,puntuacionCalculo: Int,puntuacionIngles: Int,puntuacionBandera: Int,
        puntuacionParejas: Int,numVidas: Int) {
        this.nombre = nombre
        this.contrasena = contrasena
        this.puntuacionCalculo = puntuacionCalculo
        this.puntuacionIngles = puntuacionIngles
        this.puntuacionBandera = puntuacionBandera
        this.puntuacionParejas = puntuacionParejas
        this.numVidas = numVidas
    }

    /**
     * Crea un usuario con el nombre y la contraseña, y el resto de atributos inicializados a cero o al valor por defecto.
     *
     * @param nombre Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     */
    constructor(nombre: String, contrasena: String) {
        this.nombre = nombre
        this.contrasena = contrasena
        puntuacionCalculo = 0
        puntuacionBandera = 0
        puntuacionIngles = 0
        puntuacionParejas = 0
        numVidas = 3
    }
    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    fun getNombre(): String{
        return(nombre)
    }
    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     * @return No devuelve ningún valor.
     */
    fun setNombre(nombre: String){
        this.nombre=nombre
    }
    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    fun getContrasena(): String{
        return(contrasena)
    }
    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La contraseña del usuario.
     * @return No devuelve ningún valor.
     */
    fun setContrasena(contrasena: String){
        this.contrasena=contrasena
    }
    /**
     * Obtiene la puntuación de cálculo del usuario.
     *
     * @return La puntuación de cálculo del usuario.
     */
    fun getPuntuacionCalculo(): Int{
        return(puntuacionCalculo)
    }
    /**
     * Establece la puntuación de cálculo del usuario.
     *
     * @param puntuacionCalculo La puntuación de cálculo del usuario.
     * @return No devuelve ningún valor.
     */
    fun setPuntuacionCalculo(puntuacionCalculo: Int){
        this.puntuacionCalculo=puntuacionCalculo
    }
    /**
     * Obtiene la puntuación de bandera del usuario.
     *
     * @return La puntuación de bandera del usuario.
     */
    fun getPuntuacionBandera(): Int{
        return(puntuacionBandera)
    }
    /**
     * Establece la puntuación de bandera del usuario.
     *
     * @param puntuacionBandera La puntuación de bandera del usuario.
     * @return No devuelve ningún valor.
     */
    fun setPuntuacionBandera(puntuacionBandera: Int){
        this.puntuacionBandera=puntuacionBandera
    }
    /**
     * Obtiene la puntuación de inglés del usuario.
     *
     * @return La puntuación de inglés del usuario.
     */
    fun getPuntuacionIngles(): Int{
        return(puntuacionIngles)
    }
    /**
     * Establece la puntuación de inglés del usuario.
     *
     * @param puntuacionIngles La puntuación de inglés del usuario.
     * @return No devuelve ningún valor.
     */
    fun setPuntuacionIngles(puntuacionIngles: Int){
        this.puntuacionIngles=puntuacionIngles
    }
    /**
     * Obtiene la puntuación de parejas del usuario.
     *
     * @return La puntuación de parejas del usuario.
     */
    fun getPuntuacionParejas(): Int{
        return(puntuacionParejas)
    }
    /**
     * Establece la puntuación de parejas del usuario.
     *
     * @param puntuacionParejas La puntuación de parejas del usuario.
     * @return No devuelve ningún valor.
     */
    fun setPuntuacionParejas(puntuacionParejas: Int){
        this.puntuacionParejas=puntuacionParejas
    }
    /**
     * Obtiene el número de vidas del usuario.
     *
     * @return El número de vidas del usuario.
     */
    fun getVidas(): Int{
        return(numVidas)
    }
    /**
     * Establece el número de vidas del usuario.
     *
     * @param numVidas El número de vidas del usuario.
     * @return No devuelve ningún valor.
     */
    fun setVidas(numVidas: Int){
        this.numVidas=numVidas
    }
}
