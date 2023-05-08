package com.reyprojects.tutoacademy_ma

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Preview
@Composable
fun PantallaPrincipal(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {TopBar(scope, scaffoldState)},
        drawerContent = {  DrawerContent(
            scope,
            scaffoldState,
            navController
        )
        },
        backgroundColor = colorResource(id = R.color.background)
    ){
        it
        NavigationHost(navController)
    }

}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    TopAppBar (
        title = { Text(text = "TutoAcademy", color = Color.Black)},
        backgroundColor = colorResource(id=R.color.white),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de Menú")
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )
        }
    )
}

@Composable
private fun DrawerContent(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {


    val itemsList = prepareNavigationDrawerItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background_drawer)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {

        item {

            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.chayanne),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = "Bryan Smith Colorado Lopez",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            // user's email
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
                text = "bcolorado@unal.edu.co",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )


        }

        items(itemsList) { item ->
            NavigationListItem(item = item){
                navController.navigate(item.Route){
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }
    }
}
@Composable
private fun NavigationListItem(
    item: NavigationDrawerItem,
    onItemClick: (NavigationDrawerItem) -> Unit
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            },
        onClick = { onItemClick( item )}
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(size = 28.dp),
                painter = item.image,
                contentDescription = null,
                tint = colorResource(id = R.color.black)
            )



            // label
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = item.label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.black)
            )
        }


    }
}
@Composable
private fun prepareNavigationDrawerItems(): List<NavigationDrawerItem> {
    val itemsList = arrayListOf<NavigationDrawerItem>()

    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla1.image),
            label = Destinos.Pantalla1.title,
            Route = Destinos.Pantalla1.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla2.image),
            label = Destinos.Pantalla2.title,
            Route = Destinos.Pantalla2.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla3.image),
            label = Destinos.Pantalla3.title,
            Route = Destinos.Pantalla3.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla4.image),
            label = Destinos.Pantalla4.title,
            Route = Destinos.Pantalla4.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.logout),
            label = "Cerrar Sesión",
            Route = "pantalla1"
        )
    )

    return itemsList
}

data class NavigationDrawerItem(
    val image: Painter,
    val label: String,
    val Route: String,
)
