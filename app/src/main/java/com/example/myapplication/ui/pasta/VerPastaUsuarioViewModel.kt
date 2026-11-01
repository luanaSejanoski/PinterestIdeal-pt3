package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Pin
import com.example.myapplication.data.repository.PastaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerPastaUsuarioViewModel(
        private val repository: PastaRepository,
        private val idPasta: Int
): ViewModel() {

        private  val _uiState = MutableStateFlow(VerPastaUsuarioUiState())
        val  uiState: StateFlow<VerPastaUsuarioUiState> = _uiState.asStateFlow()

        init{
            viewModelScope.launch {
                val pasta = repository.buscarPastaId(idPasta)
                _uiState.update { it.copy(pasta = pasta) }

                repository.buscarPinsPasta(idPasta).collect {
                        listaPins ->
                    _uiState.update {
                        it.copy(totalPinsPasta = listaPins.size , pins = listaPins )
                    }
                }
            }
            }

            fun aoClicarAmpliarImagem(pin: Pin) {
                _uiState.update { it.copy(imagemAmpliada = pin) }
            }

            fun fecharImagemAmpliada() {
                _uiState.update { it.copy(imagemAmpliada = null) }
            }

}