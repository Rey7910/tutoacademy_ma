package com.reyprojects.tutoacademy_ma


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.reyprojects.tutoacademy_ma.type.ProfileInput
import com.reyprojects.tutoacademy_ma.type.Request
import com.reyprojects.tutoacademy_ma.type.ServiceInput
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

import java.time.DayOfWeek
import java.time.LocalDate

fun getServices() = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()


        val response = apolloClient.query(AllServicesQuery()).execute()


        val gson = Gson()
        jsonServices = JsonParser.parseString( gson.toJson(response.data)) as JsonObject




    }catch (e: ApolloException){
        Log.d("Query Services Response",e.toString())
    }

}

@OptIn(DelicateCoroutinesApi::class)
fun CreateService(service: ServiceInput) = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()
        Log.d("Tuto","client builded well")

        val response = apolloClient.mutation(CreateServiceMutation(service)).execute()
        Log.d("Mutation Create Service",response.data.toString())

    }catch (e: ApolloException){
        Log.d("New Service Response",e.toString())
    }

}


fun getRequests() = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()


        val response = apolloClient.query(AllRequestsQuery()).execute()


        val gson = Gson()
        jsonRequest = JsonParser.parseString( gson.toJson(response.data)) as JsonObject




    }catch (e: ApolloException){
        Log.d("Query Request Response",e.toString())
    }

}


fun getProfiles() = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()


        val response = apolloClient.query(AllProfilesQuery()).execute()


        val gson = Gson()
        jsonAllProfiles = JsonParser.parseString( gson.toJson(response.data)) as JsonObject




    }catch (e: ApolloException){
        Log.d("Query Profiles Response",e.toString())
    }

}

fun getSingleProfile(googleid: String) = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()


        val response = apolloClient.query(GetProfileQuery(googleid)).execute()


        val gson = Gson()
        jsonSingleProfile = JsonParser.parseString( gson.toJson(response.data)) as JsonObject




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
