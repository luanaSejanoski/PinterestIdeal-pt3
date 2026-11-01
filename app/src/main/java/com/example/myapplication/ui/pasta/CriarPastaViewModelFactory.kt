package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.PinRepository


class CriarPastaViewModelFactory(
    private val repository: PastaRepository,
    private val idUsuario: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CriarPastaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CriarPastaViewModel(repository, idUsuario ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}