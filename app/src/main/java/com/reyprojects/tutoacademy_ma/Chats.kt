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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberImagePainter
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.toJson
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson


var Chats:String= ""
val parcial_message = "hola, necesito una tutoria"
val sender = "Usuario Random"
val receiver = "Miguel Angel Puentes"


data class Message(val text: String, val sender: String)
data class AvailableChat(val googleid: String, val fullname: String, val image: String)

val AvailableChats = mutableListOf<AvailableChat>()

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
fun getAllChats(id: String) = GlobalScope.async {
    try{
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://e117-186-84-88-227.ngrok-free.app/graphql")
            .build()
        Log.d("Tuto","client builded well")
        val response = apolloClient.query(GetChatUserQuery(id)).execute()
        val gson = Gson()
        val json = gson.toJson(response.data)
        Log.d("Query Response",response.data.toString())
        Log.d("Query Conversion",json)
        Chats = json

    }catch (e: ApolloException){
        Log.d("Query Response",e.toString())
    }

}


@Preview(showSystemUi = true)
@Composable
fun Chats(){

    val scrollState = rememberLazyListState()

    getAllChats(current_user?.googleId.toString())
    AvailableChats.clear()

    try{
        Log.d("Chat Tuto","Everything good")
        val jsonObject = JSONObject(Chats)
        val chatsArray = jsonObject.getJSONArray("getChatUser")


        for (i in 0 until chatsArray.length()) {
            val chatObject = chatsArray.getJSONObject(i)
            val chatId = chatObject.getInt("chatId")
            Log.d("TutoGod", chatId.toString())
            val fullname_sender = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("sender")
                .getString("fullname")
            Log.d("full name sender", fullname_sender)
            val id_sender = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("sender")
                .getJSONObject("userID").getString("googleId")
            Log.d("id sender ", id_sender)
            val image_sender = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("sender")
                .getJSONObject("userID").getString("imageUrl")
            Log.d("id sender ", image_sender)
            val fullname_receiver = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("receiver")
                .getString("fullname")
            Log.d("fullname receiver", fullname_receiver)
            val id_receiver = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("receiver")
                .getJSONObject("userID").getString("googleId")
            Log.d("TutoGod", id_receiver)
            val image_receiver = jsonObject.getJSONArray("getChatUser")
                .getJSONObject(i)
                .getJSONObject("receiver")
                .getJSONObject("userID").getString("imageUrl")
            Log.d("id sender ", image_receiver)

            if(id_sender!= current_user?.googleId.toString()){
                val chat = AvailableChat(id_sender, fullname_sender, image_sender)
                AvailableChats.add(chat)
            }else{
                val chat = AvailableChat(id_receiver, fullname_receiver, image_receiver)
                AvailableChats.add(chat)
            }

        }




    }catch(e: Exception){
        Log.d("TutoAAAA",e.toString())

    }
    
    Column{

        headerSelectChat()
        Spacer(modifier = Modifier
            .height(30.dp))

        if(AvailableChats.isEmpty()){
            Text("No tienes chats Disponibles")
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier.weight(1f),
        ) {
            items(AvailableChats) { chat ->
                chatBox(chat)
                Spacer(modifier = Modifier
                    .height(30.dp))
            }
        }


    }



}

@Composable
fun ChatContent(){
    val scrollState = rememberLazyListState()

    Column{

        headerChat(senderGivenName = sender)

        LazyColumn(
            state = scrollState,
            modifier = Modifier.weight(1f),
            reverseLayout = true // Muestra los elementos en orden inverso
        ) {
            items(messages) { message ->
                if(message.sender=="sender"){
                    senderMessage(message = message.text)
                }else{
                    receiverMessage(message = message.text)
                }
            }
        }

        sendButton(modifier = Modifier
            .align(Alignment.End) // Alinea el botón en la esquina inferior derecha
            .padding(30.dp) // Añade un margen al botón
            .clip(CircleShape) // Agrega una forma circular al botón
            .padding(8.dp))
    }
}

@Composable
fun chatBox(chat: AvailableChat){
    Button(
        onClick = {
            println("going to ${chat.googleid} chat")
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(251, 196, 3),
            contentColor = Color.Black
        ) ,modifier = Modifier.padding(start = 5.dp)
            .fillMaxWidth()

    ) {
        Image(
            rememberImagePainter(chat.image),
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .size(60.dp))
        Spacer(modifier = Modifier
            .width(30.dp))
        Text(text = "${chat.fullname}")
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
        .padding(8.dp)
        .fillMaxWidth()
        .background(Color.Gray)) {
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


@Composable
fun headerSelectChat(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color(251, 196, 3))
        .padding(10.dp)) {

        Row {

            Box(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxWidth()
                   // .offset(x = 8.dp, y = 9.dp) // apply the offset here
            ) {
                Text(text = "Chats Disponibles", fontSize = 25.sp)
            }

        }

    }
}
