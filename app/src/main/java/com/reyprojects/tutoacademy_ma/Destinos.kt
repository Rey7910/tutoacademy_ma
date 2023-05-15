package com.reyprojects.tutoacademy_ma


sealed class Destinos(
    val image : Int,
    val title: String,
    val ruta: String
) {
    object Pantalla1: Destinos(R.drawable.begin, "Inicio","pantalla1")
    object Pantalla2: Destinos(R.drawable.ic_profile, "Perfiles","pantalla2")
    object Pantalla3: Destinos(R.drawable.calendar, "Mis tutorias","pantalla3")
    object Pantalla4: Destinos(R.drawable.chat, "Chats","pantalla4")

    object Pantalla5: Destinos(R.drawable.chat, "chat_content","pantalla5")

    object Pantalla6: Destinos(R.drawable.chat, "missing_profile","pantalla6")
    object Pantalla7: Destinos(R.drawable.chat, "profile_form","pantalla7")

    object Pantalla8: Destinos(R.drawable.chat, "ret_home","pantalla8")




}