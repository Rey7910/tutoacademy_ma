package com.reyprojects.tutoacademy_ma

sealed class Destinos(
    val icon : Int,
    val title: String,
    val ruta: String
) {
    object Pantalla1: Destinos(R.drawable.ic_profile, "Inicio","pantalla1")
    object Pantalla2: Destinos(R.drawable.ic_profile, "Perfiles","pantalla2")
    object Pantalla3: Destinos(R.drawable.ic_profile, "Mis Tutorias","pantalla3")
    object Pantalla4: Destinos(R.drawable.ic_profile, "Chats","pantalla4")

}