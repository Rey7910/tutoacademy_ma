package com.reyprojects.tutoacademy_ma

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

@Composable
fun SearchScreen(navController: NavHostController, mainViewModel: MainViewModel){


    Log.d("Searched Text", search)
    val allProfiles = jsonAllProfiles?.get("allProfiles")?.asJsonArray

    LazyColumn(){

        allProfiles?.forEach{
            element ->
            item {

                if (element?.asJsonObject?.get("fullname")?.asString?.contains(search, ignoreCase = true ) == true){

                    val profileImage = element?.asJsonObject?.get("userID")?.asJsonObject?.get("imageUrl")?.asString
                    val googleId = element?.asJsonObject?.get("userID")?.asJsonObject?.get("googleId")?.asString
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                if (googleId != null) {
                                    getSingleProfile(googleId)
                                }
                                //getAllChats(googleId.toString())





                                navController.navigate("${Destinos.Pantalla2.ruta}/${googleId}")


                            },

                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start


                    ){


                        Image(
                            rememberImagePainter(profileImage),
                            contentDescription = "Imagen de perfil",
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(CircleShape)
                                .size(50.dp))

                        Text( text = "" + element?.asJsonObject?.get("fullname")?.asString)
                    }

                }






            }


        }
    }

}