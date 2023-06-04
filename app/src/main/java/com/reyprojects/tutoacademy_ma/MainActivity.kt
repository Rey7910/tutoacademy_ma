package com.reyprojects.tutoacademy_ma

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.unit.dp
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import androidx.navigation.compose.*
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.reyprojects.tutoacademy_ma.type.ProfileInput
import com.reyprojects.tutoacademy_ma.type.ScheduleSchemaInput
import com.reyprojects.tutoacademy_ma.type.SkillsSchemaInput
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import com.apollographql.apollo3.api.Optional
import com.google.gson.Gson
import com.reyprojects.tutoacademy_ma.type.UserInput
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection

var profilebooelan = false
var current_user by mutableStateOf<UserInput?>(null)
var current_profile by mutableStateOf<ProfileInput?>(null)
val urlGraph = "https://tutoacademy-ag-rp-oadjztiq2a-uc.a.run.app/graphql"
var jsonProfile = ""
var navegated_profile = false




@Composable
fun logoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = "Cerrar sesión",
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}



class MainActivity : ComponentActivity() {
    private var isUserLoggedIn by mutableStateOf(false)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            Surface(modifier = Modifier.fillMaxSize()){

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            loginScreen(navController)
                        }
                        composable("home") {
                            PantallaPrincipal(mainViewModel = MainViewModel()) {
                                logout(
                                    navController
                                )
                            }
                        }
                    }


            }

        }

    }
    private fun logout(navController: NavHostController) {
        isUserLoggedIn = false
        profilebooelan = false
        current_user = null
        current_profile = null
        jsonProfile = ""
        navegated_profile = false
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("323525790152-hcd861ki1ffoeian63vbhvilouj9skou.apps.googleusercontent.com").build()
        GoogleSignIn.getClient(this, googleSignInOptions).signOut()

        navController.navigate("login")


    }
}





@Composable
fun loginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){



    val token = "323525790152-hcd861ki1ffoeian63vbhvilouj9skou.apps.googleusercontent.com"
    val context = LocalContext.current
    var currentImage by remember { mutableStateOf(R.drawable.logo) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try{
            val account = task.getResult(ApiException::class.java)
            //val coroutineScope = rememberCoroutineScope()
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)


            viewModel.signInWithGoogleCredential(credential){
                Log.d("TutoacademyMa","All good")
                Log.d("TutoacademyMa","Family name: "+account.familyName.toString())
                Log.d("TutoacademyMa","Email: "+account.email.toString())
                Log.d("TutoacademyMa","First name: "+account.displayName.toString())
                Log.d("TutoacademyMa","Google id: "+account.id.toString())
                Log.d("TutoacademyMa","Image url: "+account.photoUrl.toString())

                val user = UserInput(
                    googleId = account.id.toString(),
                    givenName = account.givenName.toString(),
                    familyName = account.familyName.toString(),
                    email = account.email.toString(),
                    imageUrl = account.photoUrl.toString(),
                    authStatus = true,
                    createdAt = Optional.absent(),
                    updatedAt = Optional.absent()
                )

                try{
                    login(user)
                    current_user = user

                    getProfile(user.googleId)
                    getAllChats(current_user?.googleId.toString())

                    Log.d("Json object", jsonProfile)
                    getRequests()
                    getProfiles()
                    getServices()
                    navController.navigate("home")
                }catch(e: Exception){
                    Log.d("TutoacademyMa",e.toString())
                }



            }
        }catch(e: Exception){
            Log.d("TutoacademyMa","Something wrong")
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            currentImage = R.drawable.logo
            delay(4000)
            currentImage = R.drawable.logo_fancy
            delay(2000)
        }
    }


    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ){

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                //painterResource(R.drawable.logo),
                painterResource(currentImage),
                contentDescription = null,
                modifier = Modifier
                    .height(247.dp)
                    .width(322.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text="Login",
                textAlign = TextAlign.Center,
                fontSize =  30.sp,
                color = Color.Gray,
                modifier = Modifier
                    .width(150.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                    val options = GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                    val GoogleSignInClient = GoogleSignIn.getClient(context, options)
                    launcher.launch(GoogleSignInClient.signInIntent)

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(20.dp)
            ) {

                Image(
                    painterResource(id = R.drawable.ic_google),
                    contentDescription ="google button icon",
                    modifier = Modifier.size(23.dp)
                )
                Spacer(modifier = Modifier.width(13.dp))
                Text(text = "Inicia sesión con",
                    color = Color.White,
                    fontSize =  15.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(text = "Google",
                    color = Color(251,196,3),
                    fontSize =  15.sp,

                )

            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text="Bienvenido a la versión Móvil de nuestra App, consulta tutorías y ofrece tus servicios académicos donde quieras y cuando quieras!",
                textAlign = TextAlign.Center,
                fontSize =  22.sp,
                color = Color.Black,
                modifier = Modifier.width(300.dp)
            )

        }
    }
}

