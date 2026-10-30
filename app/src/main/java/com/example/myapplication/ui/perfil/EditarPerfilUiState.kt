package com.example.myapplication.ui.perfil

import com.example.myapplication.Usuario

class EditarPerfilUiState(
    val usuario: Usuario? = null,
    val nomeExibicao: String = "",
    val nomeUsuario: String = "",
    val biografia: String = "",
    val email: String = "",
    val fotoPerfil: String? = null
)