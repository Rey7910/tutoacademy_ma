package com.reyprojects.tutoacademy_ma

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import coil.compose.rememberImagePainter
import com.google.gson.JsonObject


var jsonAllProfiles = JsonObject()
var screenActual = ""
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaPrincipal(mainViewModel: MainViewModel){

    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()


    


    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {
                 MainAppBar(
                     searchWidgetState = searchWidgetState,
                     searchTextState = searchTextState,
                     onTextChange = {
                                    mainViewModel.updateSearchTextState(newValue = it)
                     },
                     onCloseClicked = {
                         mainViewModel.updateSearchTextState(newValue = "")
                         mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)


                         if (screenActual.isNotEmpty()){
                             navController.navigate(screenActual)
                         } else{

                             navController.navigate(Destinos.Pantalla1.ruta)

                         }


                     },
                     onSearchClicked = {
                                       Log.d("Searched Text", it)
                     },
                     onSearchTriggered = {

                         mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                         Log.d("Context", navController.toString())
                         navController.navigate(Destinos.Pantalla8.ruta)
                     },
                     scope = scope,
                     scaffoldState = scaffoldState
                 )
                 },
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
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    when(searchWidgetState){
        SearchWidgetState.CLOSED -> {
            TopBar(
                scope = scope, 
                scaffoldState = scaffoldState,
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {

            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )

        }
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    onSearchClicked: () -> Unit
){
    TopAppBar (

        title = { Text(text = "Tuto Academy", color = Color.Black)},
        backgroundColor = colorResource(id=R.color.white),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de Menú")
            }
        },
        actions = {
            
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )



        }
    )
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
){

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = colorResource(id=R.color.white)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                    
                }
            },
            trailingIcon = {
                
                IconButton(
                    onClick = {
                        if(text.isNotEmpty()){
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.Black
                    )

                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )

        )

    }

}

@Composable
@Preview
fun SearchAppBarPreview(){
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}

@Composable
private fun DrawerContent(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    var itemsList = prepareNavigationDrawerItems()




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
                painter = rememberImagePainter(current_user?.imageUrl),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = current_user?.givenName.toString() + " "+ current_user?.familyName.toString() ,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            // user's email
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
                text = current_user?.email.toString(),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )


        }

        items(itemsList) { item ->
            NavigationListItem(item = item){
                navController.navigate(item.Route){
                    launchSingleTop = true
                    screenActual = item.Route

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
        onClick = {

            if((item.Route == Destinos.Pantalla4.ruta ||  item.Route == Destinos.Pantalla2.ruta || item.Route == Destinos.Pantalla3.ruta)  && jsonProfile.length<=2 && profile==false){
                item.Route = Destinos.Pantalla6.ruta
            }else if(item.Route == Destinos.Pantalla6.ruta && jsonProfile.length>2 && profile==false ){
                if(item.label == "Perfiles") item.Route = Destinos.Pantalla2.ruta
                if(item.label == "Mis tutorias") item.Route = Destinos.Pantalla3.ruta
                if(item.label == "Chats") item.Route = Destinos.Pantalla4.ruta
                profile = true
            }

            onItemClick( item )
        }
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
            Route = "${Destinos.Pantalla2.ruta}/${current_user?.googleId}"
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


@Composable
private fun prepareNavigationDrawerItemsNoProfile(): List<NavigationDrawerItem> {


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
            Route = Destinos.Pantalla6.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla3.image),
            label = Destinos.Pantalla3.title,
            Route = Destinos.Pantalla6.ruta
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = Destinos.Pantalla4.image),
            label = Destinos.Pantalla4.title,
            Route = Destinos.Pantalla6.ruta
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
    var Route: String,
)
