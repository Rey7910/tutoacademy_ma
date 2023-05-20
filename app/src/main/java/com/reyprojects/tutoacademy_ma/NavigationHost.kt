package com.reyprojects.tutoacademy_ma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reyprojects.tutoacademy_ma.Destinos.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun  NavigationHost ( navController: NavHostController){
    NavHost(navController = navController, startDestination = Pantalla1.ruta){
        composable(Pantalla1.ruta){
            Initio(navController)
        }
        composable(Pantalla2.ruta){
            Profile(navController, jsonProfile)
        }
        composable(Pantalla3.ruta){
            Mi_Tutoria(navController)
        }
        composable(Pantalla4.ruta){
            Chats(navController)
        }
        composable(Pantalla5.ruta){
            ChatContent(navController)
        }
        composable(Pantalla6.ruta){
            missingProfile(navController)
        }
        composable(Pantalla7.ruta){
            profileForm(navController)
        }
        composable(Pantalla8.ruta){
            SearchScreen(navController)
        }



    }
}