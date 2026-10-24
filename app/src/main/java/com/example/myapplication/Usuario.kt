package com.example.myapplication

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey val id: Int = 0,
    val nomeExibicao: String,
    val nomeUsuario: String,
    val biografia: String,
    val email: String,
    val foto: String?
)


