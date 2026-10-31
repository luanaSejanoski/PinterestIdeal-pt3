package com.example.myapplication.ui.perfil

import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.local.Pasta

data class PerfilUiState(
    val usuario: Usuario? = null,
    val pastasDoUsuario: List<Pasta> = emptyList(),
    val totalAmigos: Int = 0
)