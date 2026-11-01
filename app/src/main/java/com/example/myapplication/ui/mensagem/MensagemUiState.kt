package com.example.myapplication.ui.mensagem

import com.example.myapplication.data.local.Mensagem
import com.example.myapplication.data.local.Usuario

data class MensagemUiState (
    val mensagens: List<Mensagem> = emptyList(),
    val mensagem: String = "",
    val amigo: Usuario? = null,
)