package com.reyprojects.tutoacademy_ma

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.JsonObject
import com.reyprojects.tutoacademy_ma.type.Request
import java.time.LocalDate
import java.time.LocalDateTime


var jsonRequest = JsonObject()
var jsonServices = JsonObject()
val sampleEvents = mutableListOf<Event>()

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Initio(navHostController: NavHostController){


    getRequests()
    getProfiles()
    getServices()

    Log.d("Perfiles ", jsonAllProfiles.toString())

    sampleEvents.clear()


    if (jsonProfile.isNotEmpty()){
        profile = true
    }
    val after = jsonRequest.get("allRequests")?.asJsonArray
    after?.forEach{

        item ->


        val currentUser = current_user?.googleId.toString()

        val userReq = item?.asJsonObject?.get("user_req")?.asJsonObject?.get("userID")?.asJsonObject?.get("googleId")?.asString?.replace("\"" , "" )

        val tutor = item?.asJsonObject?.get("tutor")?.asJsonObject?.get("userID")?.asJsonObject?.get("googleId")?.asString?.replace("\"" , "" )

        val state = item?.asJsonObject?.get("accepted")?.asBoolean

        if ( ( (currentUser == userReq) || (currentUser == tutor ) )  and ( state == true ) )

        {
            var id = 2
            var start = LocalDateTime.now()
            var end = LocalDateTime.now()
            var message = "Without Message"
            var tutor = "Name"
            var requested = "Name"
            var createdAt = LocalDateTime.now()
            var acepted = true

            item?.asJsonObject?.get("id")?.asInt?.let {
                id = it
            }

            item?.asJsonObject?.get("created_at")?.asString?.let {

                createdAt = LocalDateTime.now()

            }

            item?.asJsonObject?.get("accepted")?.asBoolean?.let {

                acepted = it

            }

            item?.asJsonObject?.get("message")?.asString?.let {

                message = it

            }

            item?.asJsonObject?.get("scheduled_time")?.asString?.let {



                start = LocalDateTime.parse(getDateStart(it))

                end = LocalDateTime.parse(getDateEnd(it))



            }

            item?.asJsonObject?.get("tutor")?.asJsonObject?.let {

                tutor = "" + it?.get("fullname")?.toString()

            }

            item?.asJsonObject?.get("user_req")?.asJsonObject?.let {

                requested = "" + it?.get("fullname")?.toString()
            }

            sampleEvents.add(
                Event(
                    id = id,
                    end = end,
                    start = start,
                    message = message,
                    tutor = tutor,
                    requestedBy = requested,
                    createdAt = createdAt,
                    accepted = acepted ,

                    )
            )
        }


    }

    Log.d("Lista" , sampleEvents.toString())
















    LazyColumn{
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                if (profile) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 30.dp, bottom = 30.dp, start = 15.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start

                        ) {
                            Text(
                                text = "Inicio",
                                color = Color.Black,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${current_user?.givenName.toString()}! Bienvenido a tutoAcademy",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }

                } else {

                    Box(
                        modifier = Modifier
                            .size(
                                width = 200.dp,
                                height = 200.dp
                            )
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start

                        ) {
                            Text(
                                text = "Inicio",
                                color = Color.Black,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${current_user?.givenName.toString()}! Bienvenido a tutoAcademy",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }

                }


                if (!profile) {

                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 200.dp)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(verticalArrangement = Arrangement.Center) {
                            TextButton(
                                modifier = Modifier
                                    .background(Color.White)
                                    .size(width = 220.dp, height = 50.dp),
                                onClick = {
                                    try {
                                        navHostController.navigate(Destinos.Pantalla7.ruta)
                                    } catch (e: Exception) {
                                        Log.d("error", e.toString())
                                    }
                                }
                            ) {
                                Text(text = "¡Crea tu perfil!", color = Color.Black)

                            }
                        }
                    }

                }

            }
            
            if (sampleEvents.isEmpty()){


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.background)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painterResource(R.drawable.interrogacion),
                        contentDescription = null,
                        modifier = Modifier
                            .height(247.dp)
                            .width(322.dp)
                            .align(Alignment.CenterHorizontally),

                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "No tienes ninguna reunión programada",
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )


                }

            }
        }


        items(sampleEvents) { event ->

            Text(text = "Dia del evento: " + event.start.format(DayFormatter), modifier = Modifier.padding(top = 10.dp , bottom = 5.dp, start = 5.dp))
            ShowEvents(event = event)
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowEvents( event: Event ){
    Column(
        modifier = Modifier
            .padding(end = 2.dp, bottom = 2.dp)
            .background(
                colorResource(id = R.color.background_event),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(5.dp)
            .fillMaxWidth()

    ) {

        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            style = MaterialTheme.typography.caption,
        )

        Text(
            text = event.tutor,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
        )

        if (event.message != null) {
            Text(
                text = event.message,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
