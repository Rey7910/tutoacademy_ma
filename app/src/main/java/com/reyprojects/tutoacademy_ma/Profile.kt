package com.reyprojects.tutoacademy_ma

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter


@Composable
fun Profile(){
    Column(
        modifier = Modifier.padding(20.dp)
    ){
        ProfileImageAndName(current_user?.givenName.toString())
        Spacer(modifier = Modifier.height(20.dp))
        ProfileInfo()
        Spacer(modifier = Modifier.height(10.dp))
        ProfileSkills(skills = "SkillA")
        Spacer(modifier = Modifier.height(10.dp))
        ProfileTutoringSchedule(schedule = "Lunes 4pm-6pm")
    }
}

@Composable
fun ProfileImageAndName(name: String){
    Column(modifier = Modifier
        .background(colorResource(id = R.color.light_orange))
        .padding(5.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            rememberImagePainter(current_user?.imageUrl),
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
            Text("Genero: ")
            Text("Fecha de nacimiento: ")
            Text("Nacionalidad: ")
            Text("Estudios: ")
            Text("Descripción: ")
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
@Preview(showBackground = true)
fun ProfileScreen(){
    Profile()
}