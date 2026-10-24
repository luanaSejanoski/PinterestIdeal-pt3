package com.example.myapplication

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity
data class Mensagem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val texto: String,
    val remetenteId: Int,
    val destinatarioId: Int
)