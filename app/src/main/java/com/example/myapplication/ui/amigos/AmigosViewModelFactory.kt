package com.example.myapplication.ui.amigos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.pesquisa.PesquisaViewModel

class AmigosViewModelFactory (
    private val repository: UsuarioRepository,
    private val idUsuario: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PesquisaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PesquisaViewModel(
                repository,
                idUsuario
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
