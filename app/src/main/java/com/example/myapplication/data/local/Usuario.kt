package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey val id: Int = 0,
    val nomeExibicao: String,
    val nomeUsuario: String,
    val biografia: String,
    val email: String,
    val foto: String?
)