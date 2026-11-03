package com.example.myapplication.ui.perfil

import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.local.Pasta
import kotlinx.coroutines.flow.Flow

data class PastaUiModel(
    val pasta: Pasta,
    val totalPins: Int = 0,
    val imagens: List<String> = emptyList()
) {
}

data class PerfilUiState(
    val usuario: Usuario? = null,
    val pastasDoUsuario: List<PastaUiModel> = emptyList(),
    val totalAmigos: Int = 0
)