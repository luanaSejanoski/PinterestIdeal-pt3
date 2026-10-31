package com.example.myapplication.data.local

import androidx.compose.runtime.mutableStateOf
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "Pasta")
data class Pasta(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var statusPrivado: Boolean = false,
    var nome: String,
    val usuarioId: Int
   ){
    var privado by mutableStateOf(statusPrivado)
    }