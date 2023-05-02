package com.reyprojects.tutoacademy_ma

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth

    fun signInWithGoogleCredential(credential: AuthCredential, home: ()-> Unit)
    = viewModelScope.launch{
        try{
            auth.signInWithCredential(credential)
                .addOnCompleteListener{
                    task ->
                    if(task.isSuccessful){
                        Log.d("TutoacademyMa", "Working well")
                        home()
                    }

                }.addOnFailureListener{
                   Log.d("TutoacamdyMa", "Something went wrong")
                }
        }catch (ex: Exception){
            Log.d("TutoacademyMa", "exception while google auth ")
        }
    }

}