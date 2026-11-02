package com.example.myapplication.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerPerfilUsuarioViewModel (
    private val usuarioRepository: UsuarioRepository,
    private val pastaRepository: PastaRepository,
    private val idUsuario: Int
) : ViewModel() {
    private  val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val usuario = usuarioRepository.buscarUsuarioPorId(idUsuario = idUsuario)

            _uiState.update {
                it.copy(usuario = usuario)
            }
            val totalAmigos = usuarioRepository.contarAmigosUsuario(usuarioId = idUsuario)
//            totalAmigos = totalAmigos

            pastaRepository.buscarPastas(usuarioId = idUsuario).collect { pastas ->
                val pastasUi = pastas.map { pasta ->
                    PastaUiModel(
                        pasta = pasta,
                        totalPins = pastaRepository.contarPinsPasta(pasta.id),
                        imagens = pastaRepository.buscarDoisUltimosPins(pasta.id)
                            .first().map { pin -> pin.imagem }
                    )
                }

                _uiState.update { it.copy(pastasDoUsuario = pastasUi) }
            }
        }
    }
}

//pastasDoUsuario = withContext(Dispatchers.IO){
//    buscarPastas(pastaDAO, idUsuario)
//}
//
//totalAmigos = withContext(Dispatchers.IO){
//    usuarioDAO.contarAmigos(idUsuario)
//}