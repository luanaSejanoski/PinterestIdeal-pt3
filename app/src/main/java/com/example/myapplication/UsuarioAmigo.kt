package com.example.myapplication

import androidx.room3.Entity

@Entity(primaryKeys = ["usuarioId", "amigoId"])
data class UsuarioAmigo (
    val usuarioId: Int,
    val amigoId: Int
)