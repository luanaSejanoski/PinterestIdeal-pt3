package com.example.myapplication.data.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey

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