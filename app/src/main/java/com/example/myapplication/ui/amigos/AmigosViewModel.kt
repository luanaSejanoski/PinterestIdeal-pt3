package com.example.myapplication.ui.amigos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AmigosViewModel (
    private val repository: UsuarioRepository,
    private val idUsuario: Int,
): ViewModel() {

    private val _uiState = MutableStateFlow(AmigosUiState())
    val uiState: StateFlow<AmigosUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.buscarAmigosDoUsuario(idUsuario).collect { amigos ->
                _uiState.update { it.copy(amigos = amigos) }
            }
        }
    }

    fun removerAmigo(idAmigo: Int) {
        viewModelScope.launch {
            repository.removerAmigoUsuario(idUsuario, idAmigo)
        }
    }

    fun atualizarPesquisa(texto: String) {
        _uiState.update { it.copy(pesquisarP = texto) }
    }

}
