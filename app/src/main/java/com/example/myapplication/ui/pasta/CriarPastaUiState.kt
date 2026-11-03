package com.example.myapplication.ui.pasta

data class CriarPastaUiState(
    val nomePasta: String = "",
    val privado: Boolean = false,
    var nomeFocado: Boolean = false,
    var pastaCriada: Boolean = false,
)

