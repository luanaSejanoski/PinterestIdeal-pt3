package com.example.myapplication.data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import coil.disk.DiskCache
import com.example.myapplication.Pin

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