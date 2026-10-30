package com.example.myapplication.ui.pasta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.Usuario
import com.example.myapplication.data.Pasta

data class CriarPastaUiState(
    val nomePasta: String = "",
    val privado: Boolean = false,
    var nomeFocado: Boolean = false,
    var pastaCriada: Boolean = false,
)
