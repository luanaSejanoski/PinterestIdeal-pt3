package com.example.myapplication.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["usuarioId", "amigoId"])
data class UsuarioAmigo (
    val usuarioId: Int,
    val amigoId: Int
)