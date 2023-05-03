package com.reyprojects.tutoacademy_ma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Menu

import androidx.compose.runtime.Composable

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaPrincipal()
        }
    }
} */


@Preview
@Composable
fun PantallaPrincipal(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navigationItems = listOf(
        Destinos.Pantalla1,
        Destinos.Pantalla2,
        Destinos.Pantalla3,
        Destinos.Pantalla4
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {TopBar(scope, scaffoldState)},
        drawerContent = {Drawer(menu_items = navigationItems)}
    ){ it

    }

}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    TopAppBar (
        title = { Text(text = "TutoAcademy")},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de Menú")
            }
        }
    )
}

@Composable
fun Drawer(menu_items: List<Destinos>){
    Image(painterResource(id = R.drawable.chayanne),
        contentDescription = "Chayanne :D",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .height(200.dp)
            .padding(20.dp)
            .fillMaxWidth()
            .clip(CircleShape),

        )
    Column(){
        menu_items.forEach {item ->

            DrawerItem(item = item)

        }
    }
}

@Composable
fun DrawerItem(item: Destinos){
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(15.dp)
            .clip(RoundedCornerShape(12))

    ){
        Icon(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = "sex",
            Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text= item.title,
            style = MaterialTheme.typography.body1)
    }

}