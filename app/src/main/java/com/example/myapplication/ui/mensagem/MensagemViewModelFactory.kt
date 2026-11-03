package com.example.myapplication.ui.mensagem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.MensagemRepository
import com.example.myapplication.data.repository.UsuarioRepository

class MensagemViewModelFactory(
    private val repository: MensagemRepository,
    private val usuarioRepository: UsuarioRepository,
    private val idUsuario: Int,
    private val idAmigo: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MensagemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MensagemViewModel(
                repository,
                usuarioRepository,
                idUsuario,
                idAmigo
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}