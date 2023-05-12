package com.reyprojects.tutoacademy_ma

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.reyprojects.tutoacademy_ma.type.UserInput
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

var Chats:String = ""
val parcial_message = "hola, necesito una tutoria"
val sender = "Bryan Smith Colorado"
val receiver = "Miguel Angel Puentes"

@OptIn(DelicateCoroutinesApi::class)
fun getAllChats() = GlobalScope.async {
    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://b392-186-84-88-227.ngrok-free.app/graphql")
            .build()
        Log.d("Tuto","client builded well")
        //val response = apolloClient.query(GetUsersQuery()).execute()
        val response = apolloClient.query(GetChatsQuery()).execute()
        Log.d("Query Response",response.data.toString())
        Chats = response.data.toString()
    }catch (e: ApolloException){
        Log.d("Query Response",e.toString())
    }

}

@Preview
@Composable
fun Chats(){

    Column{
        Box(modifier = Modifier.fillMaxWidth().background(Color(251,196,3)).padding(4.dp)){
            //Text("Aqui van los chats")

            Row{
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(1.dp, Color.Black, CircleShape)   // add a border (optional)
                )
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .fillMaxWidth()
                        .offset(x = 8.dp, y = 7.dp) // apply the offset here
                ) {
                    Text(text = sender)
                }

            }

        }

        Spacer(modifier = Modifier.width(15.dp).height(15.dp))

        Box(modifier = Modifier

            .border(6.dp,Color.Transparent,CircleShape)
            .offset(x = 260.dp)
            .padding(15.dp)
            .background(Color(0, 132, 255))
            ){
            Text(text=parcial_message,color = Color.White, fontSize = 10.sp)
        }



    }


}

