package com.example.myapplication.ui.perfil

import com.example.myapplication.Usuario
import com.example.myapplication.data.Pasta

data class PerfilUiState(
    val usuario: Usuario? = null,
    val pastasDoUsuario: List<Pasta> = emptyList(),
    val totalAmigos: Int = 0
)