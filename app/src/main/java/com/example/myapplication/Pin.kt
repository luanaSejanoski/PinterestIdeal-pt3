package com.example.myapplication

import android.net.Uri
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "Pin")
data class Pin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var titulo: String = "",
    val imagem: String,
    val largura: Int,
    val altura: Int
)
