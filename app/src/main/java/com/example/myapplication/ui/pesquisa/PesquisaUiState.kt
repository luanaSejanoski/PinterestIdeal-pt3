package com.example.myapplication.ui.pesquisa

import com.example.myapplication.data.local.Usuario

data class PesquisaUiState (
    val usuario: Usuario? = null,
    val idsAmigos: List<Int> = emptyList(),
    val listaUsuarios: List<Usuario> = emptyList(),
    val pesquisarP: String = ""
)