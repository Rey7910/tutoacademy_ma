package com.reyprojects.tutoacademy_ma


import android.os.Build
import androidx.annotation.RequiresApi

import java.time.DayOfWeek
import java.time.LocalDate



@RequiresApi(Build.VERSION_CODES.O)
fun getDateStart(time: String): String{

    val listTime = time.split(", ")

    val date = nearDate(listTime[0]).toString()

    return date + 'T' + listTime[1].split(" - ")[0]
}


@RequiresApi(Build.VERSION_CODES.O)
fun getDateEnd(time: String): String{

    val listTime = time.split(", ")

    val date = nearDate(listTime[0]).toString()

    return date + 'T' + listTime[1].split(" - ")[1]
}

@RequiresApi(Build.VERSION_CODES.O)
fun nearDate(dia: String): LocalDate {
    val hoy = LocalDate.now()
    val diaSemana = when (dia.lowercase()) {
        "lunes" -> DayOfWeek.MONDAY
        "martes" -> DayOfWeek.TUESDAY
        "miércoles" -> DayOfWeek.WEDNESDAY
        "jueves" -> DayOfWeek.THURSDAY
        "viernes" -> DayOfWeek.FRIDAY
        "sábado" -> DayOfWeek.SATURDAY
        "domingo" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Día de la semana no válido")
    }
    val diferenciaDias = diaSemana.value - hoy.dayOfWeek.value
    val diasParaSumar = if (diferenciaDias >= 0) diferenciaDias else 7 + diferenciaDias
    return hoy.plusDays(diasParaSumar.toLong())
}
