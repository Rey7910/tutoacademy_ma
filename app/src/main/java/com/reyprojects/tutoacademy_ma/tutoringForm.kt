package com.reyprojects.tutoacademy_ma

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.apollographql.apollo3.api.Optional
import com.reyprojects.tutoacademy_ma.type.ProfileInput
import com.reyprojects.tutoacademy_ma.type.RequestInput

var day = "Lunes"
var startHour = "07:00"
var finishHour = "08:00"

@Composable
fun tutoringForm(navController: NavHostController){
    var message by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.height(40.dp))

        androidx.compose.material.Text(
            "Solicitar tutoria",
            fontSize = 25.sp, fontWeight = FontWeight.Bold,
            color = Color(251, 196, 3)
        )

        Spacer(modifier=Modifier.height(20.dp))

        TextField(
            value = message,
            onValueChange = {
                message = it
            },
            placeholder = { androidx.compose.material.Text(text = "Descripción de la tutoria requerida*") },
            modifier = Modifier.width(275.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))
        SelectDay()
        Spacer(modifier=Modifier.height(20.dp))
        SelectHours()
        Spacer(modifier=Modifier.height(20.dp))
        Button(onClick = {

            Log.d("user_req",current_user?.googleId.toString())
            Log.d("tutor",userIdSearchProfile)
            Log.d("message",message.text)
            Log.d("scheduled_time","${day}, ${startHour} - ${finishHour}")
            Log.d("accepted",true.toString())

            val request = RequestInput(
                user_req = current_user?.googleId.toString(),
                tutor = userIdSearchProfile,
                message = message.text,
                scheduled_time ="${day}, ${startHour} - ${finishHour}",
                accepted = true.toString()
            )

            Log.d("Request object input",request.toString())

            try{
                CreateRequest(request)
                Thread.sleep(2000)
                getRequests()
                Thread.sleep(3000)
                navController.navigate("pantalla1")
            }catch(e: Exception){
                Log.d("Create Request Error",e.toString())
            }

        },
            colors = ButtonDefaults.buttonColors(backgroundColor = (colorResource(id = R.color.light_orange)))
        ){
            androidx.compose.material.Text("Guardar tutoria")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDay(){
    val listItems = arrayOf("Lunes", "Martes", "Miercoles","Jueves","Viernes","Sabado", "Domingo")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    // box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { androidx.compose.material.Text(text = "Dia") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.width(275.dp)
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    day = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    androidx.compose.material.Text(text = selectedOption)

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectHours(){
    val listItems = arrayOf("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00","10:00","11:00","12:00",
                            "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded_1 by remember {
        mutableStateOf(false)
    }
    var expanded_2 by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem_1 by remember {
        mutableStateOf(listItems[0])
    }
    var selectedItem_2 by remember {
        mutableStateOf(listItems[0])
    }

    // box for start hour
    ExposedDropdownMenuBox(
        expanded = expanded_1,
        onExpandedChange = {
            expanded_1 = !expanded_1
        }
    ) {
        // text field
        TextField(
            value = selectedItem_1,
            onValueChange = {},
            readOnly = true,
            label = { androidx.compose.material.Text(text = "Hora de inicio") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded_1
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.width(275.dp)
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded_1,
            onDismissRequest = { expanded_1 = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem_1 = selectedOption
                    startHour = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded_1 = false
                }) {
                    androidx.compose.material.Text(text = selectedOption)

                }
            }
        }
    }

    Spacer(modifier=Modifier.height(20.dp))

    // box for finish hour
    ExposedDropdownMenuBox(
        expanded = expanded_2,
        onExpandedChange = {
            expanded_2 = !expanded_2
        }
    ) {
        // text field
        TextField(
            value = selectedItem_2,
            onValueChange = {},
            readOnly = true,
            label = { androidx.compose.material.Text(text = "Hora de fin") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded_2
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.width(275.dp)
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded_2,
            onDismissRequest = { expanded_2 = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem_2 = selectedOption
                    finishHour = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded_2 = false
                }) {
                    androidx.compose.material.Text(text = selectedOption)

                }
            }
        }
    }
}
