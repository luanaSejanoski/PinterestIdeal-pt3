package com.example.myapplication.data

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import coil.disk.DiskCache
import com.example.myapplication.Pin

data class Pasta(
    val id:Int,
    var statusPrivado: Boolean = false,
    var nome: String,
    var pins: SnapshotStateList<Int> = mutableStateListOf()
   ){
    var privado by mutableStateOf(statusPrivado)
    }