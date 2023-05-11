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
            Initio()
        }
        composable(Pantalla2.ruta){
            Profile()
        }
        composable(Pantalla3.ruta){
            Mi_Tutoria()
        }
        composable(Pantalla4.ruta){
            Chats()
        }

    }
}