package com.example.myapplication.data.local

import androidx.room3.Entity

@Entity(primaryKeys = ["pastaId", "pinId"])
data class PastaPin(
    val pastaId: Int,
    val pinId:Int
)