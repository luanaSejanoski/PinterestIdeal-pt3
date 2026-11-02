package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pin")
data class Pin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var titulo: String = "",
    val imagem: String,
    val largura: Int,
    val altura: Int
)