package com.reyprojects.tutoacademy_ma

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val shouldUpdate = mutableStateOf(false)
}