package com.reyprojects.tutoacademy_ma


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun missingProfile(navHostController: NavHostController){
    Column(modifier =Modifier.fillMaxWidth().background(Color.White).fillMaxSize()){
        Image(
            painterResource(R.drawable.doubtlogo),
            contentDescription = null,
            modifier = Modifier
                .height(247.dp)
                .width(322.dp).align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "No tienes acceso a esta funcionalidad \uD83D\uDEAB",
            fontSize = 30.sp,
            color = Color(251, 196, 3),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Aún no haz configurado tú perfil",
            fontSize = 23.sp,
            color = Color.Black,
            textAlign = TextAlign.Center ,
            modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick={
                navHostController.navigate(Destinos.Pantalla7.ruta)
            },
            modifier = Modifier.fillMaxWidth().padding(start=40.dp, end=40.dp)
        ){
            Text(text = "Crear Perfil",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }


    }
}