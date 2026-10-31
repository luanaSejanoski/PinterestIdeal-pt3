package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.PinRepository


class VerPastaUsuarioViewModelFactory(
    private val repository: PastaRepository,
    private val idPasta: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerPastaUsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VerPastaUsuarioViewModel(repository,  idPasta) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}