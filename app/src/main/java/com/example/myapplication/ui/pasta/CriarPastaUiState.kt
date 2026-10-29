package com.example.myapplication.ui.pasta

import com.example.myapplication.Usuario
import com.example.myapplication.data.Pasta

data class CriarPastaUiState(
    val nomePasta: String = "",
    val privado: Boolean = false,
    val pastaCriada: Boolean = false,
)
