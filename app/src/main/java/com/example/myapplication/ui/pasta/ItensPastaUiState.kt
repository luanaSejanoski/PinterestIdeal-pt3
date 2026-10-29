package com.example.myapplication.ui.pasta

import com.example.myapplication.Pin
import com.example.myapplication.R
import com.example.myapplication.data.Pasta

data class ItensPastaUiState(
    val pasta: Pasta? = null,
    val pins: List<Pin> = emptyList(),
    val totalPinsPasta: Int = 0,
    val imagemAmpliada: Pin? = null,
    val nomePasta: String = "",
    val privacidadePasta: Boolean = false,
    val mostrarDialogoMudarNome: Boolean = false,
    val mostrarDialogoExcluir: Boolean = false,
    val novoNome: String = "",
    val editandoPin: Boolean = false,
    val novoTitulo: String = ""
    ){
    val iconePasta: Int
        get() = if (privacidadePasta) R.drawable.pasta_privada
        else R.drawable.pasta_publica

    val textoPrivacidade: String
        get() = if (privacidadePasta) "Pasta privada"
        else "Pasta pública"
}
