package com.example.myapplication.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class TelaPerfilViewModel (
    private val usuarioRepository: UsuarioRepository,
    private val pastaRepository: PastaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        recarregarUsuario()

        viewModelScope.launch {
            usuarioRepository.contarAmigosUsuario(usuarioId = 0)
                .collect { totalAmigos ->
                    _uiState.update { it.copy(totalAmigos = totalAmigos) }
                }
        }

        viewModelScope.launch {
            pastaRepository.observarPastas(usuarioId = 0).collect {
                recarregarPastas()
            }

            viewModelScope.launch {
                pastaRepository.observarLigacoes().collect {
                    recarregarPastas()
                }
            }
        }
    }

    fun recarregarUsuario() {
        viewModelScope.launch {
            val usuario = usuarioRepository.buscarUsuarioPorId(idUsuario = 0)
            _uiState.update { it.copy(usuario = usuario) }
        }
    }

    fun recarregarPastas() {
        viewModelScope.launch {
            val pastas = pastaRepository.buscarPastas(usuarioId = 0).first()


            val pastasUi = pastas.map { pasta ->
                PastaUiModel(
                    pasta = pasta,
                    nomePasta = pasta.nome,
                    totalPins = pastaRepository.contarPinsPasta(pasta.id),
                    imagens = pastaRepository.buscarDoisUltimosPins(pasta.id)
                        .first().map { pin -> pin.imagem }
                )
            }

            _uiState.update { it.copy(pastasDoUsuario = pastasUi) }
        }
    }
}
