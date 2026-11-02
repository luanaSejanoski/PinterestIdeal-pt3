package com.example.myapplication.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditarPerfilViewModel(private val usuarioRepository : UsuarioRepository) : ViewModel() {
    private  val _uiState = MutableStateFlow(EditarPerfilUiState())
    val uiState: StateFlow<EditarPerfilUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val usuario = usuarioRepository.buscarUsuarioPorId(idUsuario = 0)

            if (usuario != null){
                _uiState.update {
                    it.copy(
                        usuario = usuario,
                        nomeExibicao = usuario.nomeExibicao,
                        nomeUsuario = usuario.nomeUsuario,
                        biografia = usuario.biografia ?: "",
                        email = usuario.email ?: "",
                        fotoPerfil = usuario.foto
                    )
                }
            }
        }
    }

    fun aoClicarSalvar(onSalvoComSucesso: () -> Unit){

        val state = _uiState.value

        val usuarioAtual = state.usuario ?: return

        viewModelScope.launch{
                usuarioRepository.atualizarDadosUsuario(
                usuario = usuarioAtual,
                nomeExibicao = state.nomeExibicao,
                nomeUsuario = state.nomeUsuario,
                biografia = state.biografia ?: "",
                email = state.email ?: "",
                fotoPerfil = state.fotoPerfil
            )

            onSalvoComSucesso()
        }
    }

    fun onNomeExibicaoChange(novoNomeExibicao: String){
        _uiState.update {
            it.copy(nomeExibicao = novoNomeExibicao)
        }
    }

    fun onNomeUsuarioChange(novoNomeUsuario: String){
        _uiState.update {
            it.copy(nomeUsuario = novoNomeUsuario)
        }
    }

    fun onBioChange(novaBio: String){
        _uiState.update {
            it.copy(biografia = novaBio)
        }
    }

    fun onEmailChange(novoEmail: String){
        _uiState.update {
            it.copy(email = novoEmail)
        }
    }

    fun onFotoChange(novaFoto: String?){
        _uiState.update {
            it.copy(fotoPerfil = novaFoto)
        }
    }

}