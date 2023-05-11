package com.reyprojects.tutoacademy_ma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xDFDDDD)
@Composable
fun Initio(){

    val events = sampleEvents

    LazyColumn{
        item {
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
                        Text(text = "${current_user?.givenName.toString()}!! Bienvenido a tutoAcademy",
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

        items(events) { event ->

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
            .background(colorResource(id = R.color.background_event), shape = RoundedCornerShape(4.dp))
            .padding(5.dp)

    ) {

        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            style = MaterialTheme.typography.caption,
        )

        Text(
            text = event.name,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
        )

        if (event.description != null) {
            Text(
                text = event.description,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
