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
    private  val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val usuario = usuarioRepository.buscarUsuarioPorId(idUsuario = 0)
            val totalAmigos = usuarioRepository.contarAmigosUsuario(usuarioId = 0)

            _uiState.update {
                it.copy(
                    usuario = usuario,
                    totalAmigos = totalAmigos
                )
            }

//            pastaRepository.buscarPastas(usuarioId = 0).collect {
//                    pastas -> _uiState.update { it.copy(pastasDoUsuario = pastas) }
//            }

            pastaRepository.buscarPastas(usuarioId = 0).collect { pastas ->
                val pastasUi = pastas.map { pasta ->
                    PastaUiModel(
                        pasta = pasta,
                        totalPins = pastaRepository.contarPinsPasta(pasta.id),
                        imagens =
                            pastaRepository.buscarDoisUltimosPins(pasta.id)
                            .first().map { pin -> pin.imagem }
                    )
                }

                _uiState.update { it.copy(pastasDoUsuario = pastasUi) }
            }
        }
    }


}

//usuario = withContext(Dispatchers.IO) {
//    buscarUsuarioPorId(usuarioDAO, idUsuario)
//}
//
//pastasDoUsuario = withContext(Dispatchers.IO) {
//    buscarPastas(pastaDAO, idUsuario)
//}
//
//totalAmigos = withContext(Dispatchers.IO) {
//    contarAmigosUsuario(usuarioDAO, idUsuario)
//}