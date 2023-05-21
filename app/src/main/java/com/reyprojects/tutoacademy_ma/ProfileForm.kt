package com.reyprojects.tutoacademy_ma


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.reyprojects.tutoacademy_ma.type.ProfileInput
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


var gender = "Masculino"
var country = "Afghanistan"
var degree = "Primaria"

@Composable
fun profileForm(navHostController: NavHostController) {
  profile_ss(navHostController)
}


@Composable
fun profile_ss(navHostController: NavHostController){
    val scrollState = rememberLazyListState()



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize().background(Color.White).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text("Crear Perfil",
        fontSize = 25.sp, fontWeight = FontWeight.Bold,
        color = Color(251, 196, 3))
        Spacer(modifier=Modifier.height(10.dp))
        var name by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text(text = "Nombre") },
            placeholder = { Text(text = "Ingresa tu nombre") },
        )
        Spacer(modifier=Modifier.height(20.dp))

        var lastname by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = lastname,
            onValueChange = {
                lastname = it
            },
            label = { Text(text = "Apellido") },
            placeholder = { Text(text = "Ingresa tu apellido") },
            modifier = Modifier.width(275.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))
        var email by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Correo") },
            placeholder = { Text(text = "Ingresa tu correo") },
            modifier = Modifier.width(275.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))
        var description by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = { Text(text = "Descripción") },
            placeholder = { Text(text = "Cuéntanos un poco sobre ti") },
            modifier = Modifier.width(275.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))

        SelectTagGender()
        Spacer(modifier=Modifier.height(20.dp))
        SelectTagCountry()
        Spacer(modifier=Modifier.height(20.dp))
        var date by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = date,
            onValueChange = {
                date = it
            },
            label = { Text(text = "Fecha de nacimiento") },
            placeholder = { Text(text = "dd/mm/aa") },
            modifier = Modifier.width(275.dp)
        )
        Spacer(modifier=Modifier.height(20.dp))
        SelectTagDegree()
        Spacer(modifier=Modifier.height(15.dp))

        Button(onClick = {
            Log.d("name",name.text)
            Log.d("lastname",lastname.text)
            Log.d("email",email.text)
            Log.d("description",description.text)
            Log.d("gender",gender)
            Log.d("country",country)
            Log.d("date",date.text)
            Log.d("degree",degree)

            if(gender == "Masculino") gender="male"
            else if(gender == "Femenino") gender = "female"
            else if(gender =="Otro") gender = "other"

            if(degree == "Maestría o más") degree="Maestria"


            val profile = ProfileInput(
                userID = Optional.present(current_user?.googleId.toString()),
                fullname = Optional.present("${name.text} ${lastname.text}"),
                description = Optional.present(description.text),
                gender = Optional.present(gender),
                nationality = Optional.present(country),
                birthdate = Optional.present(date.text),
                degree = Optional.present(degree),
                skills = Optional.absent(),
                schedule = Optional.absent(),
                creationdate = Optional.absent()
            )



            try{
                CreateProfile(profile)
                Thread.sleep(4000)
                jsonProfile="Waiting for being assigned"
                getProfile(current_user?.googleId.toString())
                getProfiles()
                Thread.sleep(3000)
                navHostController.navigate("pantalla1")


            }catch(e: Exception){
                Log.d("Create Profile Error",e.toString())
            }




        }){
            Text("Crear Perfil")
        }
        Spacer(modifier=Modifier.height(20.dp))



    }


}

@OptIn(DelicateCoroutinesApi::class)
fun CreateProfile(profile: ProfileInput) = GlobalScope.async {

    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl(urlGraph)
            .build()
        Log.d("Tuto","client builded well")

        val response = apolloClient.mutation(CreateProfileMutation(profile)).execute()
        Log.d("Mutation Profile",response.data.toString())





    }catch (e: ApolloException){
        Log.d("Query Profile Response",e.toString())
    }

}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTagGender(){
    val listItems = arrayOf("Masculino", "Femenino", "Otro")
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
            label = { Text(text = "Género") },
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
                    gender = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)

                }
            }
        }
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTagDegree(){
    val listItems = arrayOf("Primaria", "Secundaria", "Bachiller","Pregrado","Posgrado","Maestría o más")
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
            label = { Text(text = "Grado") },
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
                    degree = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)

                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTagCountry(){
    val listItems = arrayOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Brazil",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cabo Verde",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Central African Republic (CAR)",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Comoros",
        "Democratic Republic of the Congo",
        "Republic of the Congo",
        "Costa Rica",
        "Cote d'Ivoire",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Fiji",
        "Finland",
        "France",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Greece",
        "Grenada",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kosovo",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Morocco",
        "Mozambique",
        "Myanmar (Burma)",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "North Korea",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Palestine",
        "Panama","Rusia", "Uruguay", "Venezuela")
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
            label = { Text(text = "País") },
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
                    country = selectedOption
                    Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                    expanded = false
                }) {
                    Text(text = selectedOption)

                }
            }
        }
    }
}