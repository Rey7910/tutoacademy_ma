package com.reyprojects.tutoacademy_ma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, backgroundColor = 0xDFDDDD)
@Composable
fun Initio(){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Box(
            modifier = Modifier
                .size(
                    width = 200.dp,
                    height = 200.dp
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start

            ) {
                Text(text = "Inicio",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = "Bienvenido Bryan Smith a tutoAcademy",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light)
            }
        }
        Box(
            modifier = Modifier
                .size(width = 150.dp, height = 200.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Column( verticalArrangement = Arrangement.Center){
                TextButton(
                    modifier = Modifier
                        .background(Color.White)
                        .size(width = 220.dp, height = 50.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "¡Crea tu perfil!", color = Color.Black)

                }
            }
        }
    }

}

