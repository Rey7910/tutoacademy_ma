package com.reyprojects.tutoacademy_ma

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource


sealed class Destinos(
    val image : Int,
    val title: String,
    val ruta: String
) {
    object Pantalla1: Destinos( R.drawable.begin, "Inicio","pantalla1")
    object Pantalla2: Destinos(R.drawable.ic_profile, "Perfiles","pantalla2")
    object Pantalla3: Destinos(R.drawable.calendar, "Mis Tutorias","pantalla3")
    object Pantalla4: Destinos(R.drawable.chat, "Chats","pantalla4")

}