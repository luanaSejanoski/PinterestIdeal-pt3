package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.PinRepository


class ItensPastaViewModelFactory(
    private val repository: PastaRepository,
    private val pinRepository: PinRepository,
    private val idPasta: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItensPastaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItensPastaViewModel(repository, pinRepository, idPasta) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}