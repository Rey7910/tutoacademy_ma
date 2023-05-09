package com.reyprojects.tutoacademy_ma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun Mis_tutorias(){
    Column(modifier = Modifier.fillMaxSize()) {
        CalendarMobile()
    }

}