package com.example.myapplication.ui.pesquisa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PesquisaViewModel(
    private val repository: UsuarioRepository,
    private val idUsuario: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(PesquisaUiState())
    val uiState: StateFlow<PesquisaUiState> = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            val usuario = repository.buscarUsuarioPorId(idUsuario)
            _uiState.update { it.copy(usuario = usuario) }
        }


        viewModelScope.launch {
            repository.listarUsuarios().collect { usuarios ->
                _uiState.update {
                    it.copy(
                        listaUsuarios = usuarios
                    )
                }
            }
        }

        viewModelScope.launch {
            repository.buscarAmigosDoUsuario(idUsuario).collect { amigos ->
                _uiState.update {
                    it.copy(
                        idsAmigos = amigos.map { amigo -> amigo.id }
                    )
                }
            }
        }
    }

    fun atualizarPesquisa(texto: String) {
        _uiState.update {
            it.copy(pesquisarP = texto)
        }
    }

    fun adicionarAmigo(amigo: Usuario) {
        if (amigo.id == idUsuario || amigo.id in _uiState.value.idsAmigos) {
            return
        }

        viewModelScope.launch {
            repository.adicionarAmigoBanco(idUsuario, amigo.id)

            _uiState.update {
                it.copy(
                    idsAmigos = it.idsAmigos + amigo.id
                )
            }
        }
    }

}


