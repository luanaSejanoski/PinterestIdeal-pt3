package com.example.myapplication

import android.net.Uri
import androidx.room3.PrimaryKey

data class Pin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imagem: String,
    val largura: Int,
    val altura: Int
)
