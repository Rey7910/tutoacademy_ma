package com.reyprojects.tutoacademy_ma

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

var Chats:String = ""
val parcial_message = "hola, necesito una tutoria"
val sender = "Usuario Random"
val receiver = "Miguel Angel Puentes"


data class Message(val text: String, val sender: String)
val messages = mutableListOf<Message>(
    Message("Hola","sender"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("Hola","sender"),
    Message("Hola","sender"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("holaaaa","receiver"),
    Message("Hola, necesito una tutoria, pero erda vale mia eche monda pa fina xdddddddddd\"","sender")
)
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

@Preview(showSystemUi = true)
@Composable
fun Chats(){

    val scrollState = rememberLazyListState()

    Column{

        headerChat(senderGivenName = sender)

        LazyColumn(
            state = scrollState,
            modifier = Modifier.weight(1f),
            reverseLayout = true // Muestra los elementos en orden inverso
        ) {
            items(messages) { message ->
                if (message.sender == "sender") {
                    senderMessage(message.text)
                } else {
                    receiverMessage(message.text)
                }
            }
        }

        sendButton(modifier = Modifier
            .align(Alignment.End) // Alinea el botón en la esquina inferior derecha
            .padding(16.dp) // Añade un margen al botón
            .clip(CircleShape) // Agrega una forma circular al botón
            .padding(8.dp))
    }

}


@Composable
fun senderMessage(message: String) {
    val shape = RoundedCornerShape(12.dp)
    Spacer(modifier = Modifier
        .width(30.dp)
        .height(30.dp))
    Column(modifier =  Modifier.fillMaxWidth()){
        Box(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp)
                .background(Color(0, 132, 255), shape)
                .clip(shape)
                .padding(5.dp)
                .align(Alignment.End)// Utiliza Modifier.align() en su lugar

        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }

}

@Composable
    fun receiverMessage(message: String) {
    val shape = RoundedCornerShape(12.dp)
    Spacer(modifier = Modifier
        .width(30.dp)
        .height(30.dp))
    Column(modifier =  Modifier.fillMaxWidth()){
        Box(
            modifier = Modifier
                .padding(end = 16.dp, start = 16.dp)
                .background(Color(68, 68, 68), shape)
                .clip(shape)
                .padding(5.dp)

        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }

}


@Composable
fun headerChat(senderGivenName: String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color(251, 196, 3))
        .padding(4.dp)) {
        //Text("Aqui van los chats")

        Row {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, Color.Black, CircleShape)   // add a border (optional)
            )
            Box(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxWidth()
                    .offset(x = 8.dp, y = 9.dp) // apply the offset here
            ) {
                Text(text = senderGivenName, fontSize = 20.sp)
            }

        }

    }
}

@Composable
fun sendButton(modifier: Modifier) {
    Box(modifier = Modifier

        .padding(16.dp)
        //.background(Color.Blue)
        .clip(CircleShape)
        .padding(8.dp).fillMaxWidth().background(Color.Gray)) {
        Row {
            var text by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                        modifier = Modifier
                        .width(240.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    println(text)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(251, 196, 3),
                    contentColor = Color.Black
                ) ,modifier = Modifier.padding(top = 4.dp, start = 5.dp)

            ) {
                Text(text = "enviar")
            }
        }
    }
}



