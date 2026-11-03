package com.example.myapplication.ui.mensagem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Mensagem
import com.example.myapplication.data.repository.MensagemRepository
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MensagemViewModel(
    private val repository: MensagemRepository,
    private val usuarioRepository: UsuarioRepository,
    private val idUsuario: Int,
    private val idAmigo: Int

) : ViewModel() {

    private val _uiState = MutableStateFlow(MensagemUiState())
    val uiState: StateFlow<MensagemUiState> = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            val amigo = usuarioRepository.buscarUsuarioPorId(idAmigo)

            _uiState.update {
                it.copy(amigo = amigo)
            }
            repository.buscarConversa(idUsuario, idAmigo).collect { mensagens ->
                _uiState.update {
                    it.copy(mensagens = mensagens)
                }
            }
        }
    }

    fun atualizarMensagem(texto: String) {
        _uiState.update {
            it.copy(mensagem = texto)
        }
    }

    fun enviarMensagem(texto: String) {
        val textoLimpo = texto.trim()
        if (textoLimpo.isEmpty()) return

        _uiState.update { it.copy(mensagem = "") }

            viewModelScope.launch {
                repository.inserir(Mensagem(
                    texto = textoLimpo,
                    remetenteId = idUsuario,
                    destinatarioId = idAmigo

                ))
            }
        }
    }
