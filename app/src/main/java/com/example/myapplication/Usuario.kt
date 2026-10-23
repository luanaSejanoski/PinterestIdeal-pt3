package com.example.myapplication

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf

data class Usuario(
    val id: Int,
    val nomeExibicao: String,
    val nomeUsuario: String,
    val biografia: String,
    val email: String,
    val foto: Uri?,
    val amigos: MutableList<Int> = mutableStateListOf(),
    val pastas: MutableList<Int> = mutableStateListOf()
)


