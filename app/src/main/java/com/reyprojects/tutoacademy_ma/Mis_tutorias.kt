package com.reyprojects.tutoacademy_ma

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Mi_Tutoria(navController: NavHostController){
    if(jsonProfile.length==2 && navegated_profile==false ){
        try{
            navegated_profile=true
            navController.navigate(Destinos.Pantalla6.ruta)
        }catch(e: Exception){
            Log.d("Exception",e.toString())
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        CalendarMobile()
    }

}

