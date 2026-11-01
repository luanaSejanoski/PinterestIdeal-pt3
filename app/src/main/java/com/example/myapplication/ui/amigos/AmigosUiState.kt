package com.example.myapplication.ui.amigos

import com.example.myapplication.data.local.Usuario

data class AmigosUiState (
    val amigos: List<Usuario> = emptyList(),
    val pesquisarP: String = ""
)