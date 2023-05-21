package com.reyprojects.tutoacademy_ma

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.apollographql.apollo3.api.Optional
import com.google.gson.JsonObject
import com.reyprojects.tutoacademy_ma.type.ServiceInput
import org.json.JSONObject

var fullnameProfile = "loaded"
var genderProfile = "loaded"
var nationalityProfile = "loaded"
var descriptionProfile = "loaded"
var degreeProfile = "loaded"
var birthdateProfile = "loaded"
var userIdSearchProfile = ""
var jsonSingleProfile = JsonObject()
var Url = ""

@Composable
fun Profile(navController: NavHostController, userId: String){



    getSingleProfile(userId)

    Log.d("UserId", jsonSingleProfile.toString())




    val profile = jsonSingleProfile.toString()




    try{
        val jsonObjectGeneral = JSONObject(profile)
        val jsonObject = jsonObjectGeneral.getJSONObject("getProfile")
        fullnameProfile = jsonObject.getString("fullname")
        genderProfile = jsonObject.getString("gender")
        nationalityProfile = jsonObject.getString("nationality")
        descriptionProfile = jsonObject.getString("description")
        degreeProfile = jsonObject.getString("degree")
        birthdateProfile = jsonObject.getString("birthdate")

        Url = jsonObject.getJSONObject("userID").getString("imageUrl")
        userIdSearchProfile =  jsonObject.getJSONObject("userID").getString("googleId")

        Log.d("googleidSearched", userIdSearchProfile)
        Log.d("myUserId", current_user?.googleId.toString())

    }catch(e: Exception){
        Log.d("Error Parsing Profile",e.toString())
    }


    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())

    ){
        ProfileImageAndName(fullnameProfile, Url)
        Spacer(modifier = Modifier.height(20.dp))
        ProfileInfo()
        Spacer(modifier = Modifier.height(10.dp))

        //Check if the logged user is seeing his profile
        if (current_user?.googleId == userId) {
            Log.d("Services content", jsonServices.toString())
            val after = jsonServices.get("allServices")?.asJsonArray
            var isTutor = false
            after?.forEach loop@{ item ->
                val currentGoogleId = item?.asJsonObject?.get("idProfile")?.asJsonObject?.get("userID")?.asJsonObject?.get("googleId")?.asString?.replace("\"", "")
                //Check if the user logged is a tutor
                if (userId == currentGoogleId) {
                    isTutor = true
                    return@loop
                }
            }

            if (!isTutor) {
                TurnToTutor()
            }
        }
        if(userIdSearchProfile== current_user?.googleId.toString()){
            Button(onClick = { /* */ },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "¿Quieres convertirte en tutor?")
            }
        }else{
            Button(onClick = {
                newChatBoolean = true
                currentNewChatReceiver = userIdSearchProfile
                new_chat = AvailableChat(userIdSearchProfile,fullnameProfile, Url)
                navController.navigate(Destinos.Pantalla5.ruta)

            },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Enviar mensaje")
            }

        }




    }


}

@Composable
fun ProfileImageAndName(name: String, ImageUrl : String){
    Column(modifier = Modifier
        .background(colorResource(id = R.color.light_orange))
        .padding(5.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            rememberImagePainter(ImageUrl),
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .size(120.dp))
        Text(text = "Hola " + "$name" + "!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(15.dp))
    }
}

@Composable
fun ProfileInfo() {
        Column(modifier = Modifier
            .background(colorResource(id = R.color.light_orange))
            .padding(15.dp)
            .fillMaxWidth()) {
            Text("Tu información:", fontSize = 20.sp)
            Spacer(
                modifier = Modifier.height(
                    5.dp
                )
            )
            Text("Genero: ${genderProfile}")
            Text("Fecha de nacimiento: ${birthdateProfile}")
            Text("Nacionalidad: ${nationalityProfile}")
            Text("Estudios: ${degreeProfile}")
            Text("Descripción: ${descriptionProfile}")
        }
}

@Composable
fun ProfileSkills(skills: String){
    Column(modifier = Modifier
        .background(colorResource(id = R.color.light_orange))
        .padding(15.dp)
        .fillMaxWidth()) {
        Text("Habilidades: ", fontSize = 20.sp)
        Text("$skills")
        Spacer(
            modifier = Modifier.height(
                5.dp
            )
        )
    }
}

@Composable
fun ProfileTutoringSchedule(schedule: String){
    Column(modifier = Modifier
        .background(colorResource(id = R.color.light_orange))
        .padding(15.dp)
        .fillMaxWidth()) {
        Text("Horario de tutorías: ", fontSize = 20.sp)
        Text("$schedule")
        Spacer(
            modifier = Modifier.height(
                5.dp
            )
        )
    }
}

@Composable
fun TurnToTutor() {
    var show by rememberSaveable { mutableStateOf(false) }

    Button(onClick = { show = true }) {
        Text(text = "¿Quieres convertirte en tutor?")
    }
    CreateServiceDialog(show, {show = false},
        {

        show = false
    })
}

@Composable
fun CreateServiceDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        var textFieldState by remember {
            mutableStateOf("")
        }

        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    AttemptCreateService(serviceDescription = textFieldState)
                    onConfirm()
                }) {
                    Text(text = "Guardar servicio")
                }
            },
            title = { Text(text = "Describe el servicio que ofreces como tutoria")},
            text = {
                TextField(
                    value = textFieldState,
                    onValueChange = {
                        textFieldState = it
                    },
                    placeholder = { Text(text = "Ejemplo: Enseño electronica para estudiantes universitarios") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }

}


fun AttemptCreateService(serviceDescription: String) {

    var jsonOneService = ""

    val service = ServiceInput(
        idProfile = Optional.present(current_user?.googleId.toString()),
        description = Optional.present(serviceDescription),
        serviceState = Optional.present(true)
    )

    try{
        CreateService(service)
        Thread.sleep(4000)
        jsonOneService="Waiting for being assigned"
        Thread.sleep(3000)

    }catch(e: Exception){
        Log.d("Create Profile Error",e.toString())
    }
}