package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mensagem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val texto: String,
    val remetenteId: Int,
    val destinatarioId: Int
)