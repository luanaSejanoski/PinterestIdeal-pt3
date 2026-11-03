package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PastaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CriarPastaViewModel(
    private val repository: PastaRepository,
    private val idUsuario: Int
): ViewModel(){

    private val _uiState =  MutableStateFlow(CriarPastaUiState())
    val  uiState: StateFlow<CriarPastaUiState> = _uiState.asStateFlow()

    init{

    }

    fun onNomeChange(nome: String){
        _uiState.update {
            it.copy(nomePasta = nome)
        }
    }

    fun onPrivadoChange(valor: Boolean){
        _uiState.update {
            it.copy(privado = valor)
        }
    }

    fun aoClicarCriarPasta(usuarioId: Int){
        val state = _uiState.value


        viewModelScope.launch {
            repository.criarPasta(state.nomePasta, state.privado, usuarioId)
            _uiState.update {
                it.copy(pastaCriada = true)
            }
        }
    }



}