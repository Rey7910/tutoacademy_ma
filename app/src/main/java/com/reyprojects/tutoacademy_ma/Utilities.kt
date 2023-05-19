package com.reyprojects.tutoacademy_ma


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.reyprojects.tutoacademy_ma.type.Request
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

import java.time.DayOfWeek
import java.time.LocalDate


fun getRequests() = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()


        val response = apolloClient.query(AllRequestsQuery()).execute()


        val gson = Gson()
        jsonRequest = JsonParser.parseString( gson.toJson(response.data)) as JsonObject




    }catch (e: ApolloException){
        Log.d("Query Profile Response",e.toString())
    }

}

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
        "miercoles" -> DayOfWeek.WEDNESDAY
        "jueves" -> DayOfWeek.THURSDAY
        "viernes" -> DayOfWeek.FRIDAY
        "sábado" -> DayOfWeek.SATURDAY
        "sabado" -> DayOfWeek.WEDNESDAY
        "domingo" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Día de la semana no válido")
    }
    val diferenciaDias = diaSemana.value - hoy.dayOfWeek.value
    val diasParaSumar = if (diferenciaDias >= 0) diferenciaDias else 7 + diferenciaDias
    return hoy.plusDays(diasParaSumar.toLong())
}
