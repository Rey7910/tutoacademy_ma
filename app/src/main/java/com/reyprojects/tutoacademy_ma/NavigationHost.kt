package com.reyprojects.tutoacademy_ma

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reyprojects.tutoacademy_ma.Destinos.*

@Composable
fun  NavigationHost ( navController: NavHostController){
    NavHost(navController = navController, startDestination = Pantalla1.ruta){
        composable(Pantalla1.ruta){
            Inicio()
        }
        composable(Pantalla2.ruta){
            Profile()
        }
        composable(Pantalla3.ruta){
            Mis_tutorias()
        }
        composable(Pantalla4.ruta){
            Chats()
        }

    }
}