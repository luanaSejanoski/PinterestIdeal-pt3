package com.example.myapplication.ui.pasta

import com.example.myapplication.Pin
import com.example.myapplication.data.Pasta

data class VerPastaUsuarioUiState(
    val pasta: Pasta? = null,
    val pins: List<Pin> = emptyList(),
    val totalPinsPasta: Int = 0,
    val imagemAmpliada: Pin? = null
)