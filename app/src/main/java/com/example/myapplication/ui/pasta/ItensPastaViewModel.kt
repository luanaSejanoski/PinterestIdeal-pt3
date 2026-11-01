package com.example.myapplication.ui.pasta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Pin
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.PinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItensPastaViewModel(
        private val repository: PastaRepository,
        private val pinRepository: PinRepository,
        private val idPasta: Int): ViewModel() {

        private  val _uiState = MutableStateFlow(ItensPastaUiState())
        val  uiState: StateFlow<ItensPastaUiState> = _uiState.asStateFlow()

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



            fun abrirDialogoMudarNome() {
                _uiState.update {
                    it.copy(
                        novoNome = it.pasta?.nome.orEmpty(),
                        mostrarDialogoMudarNome = true
                    )
                }
            }

            fun fecharDialogoMudarNome(){
                _uiState.update{
                    it.copy(mostrarDialogoMudarNome = false)
                }
            }

            fun onNomeChange(novoNome: String){
                _uiState.update {
                    it.copy(nomePasta =  novoNome)
                }
            }

            fun aoClicarSalvarNome() {
                val state = _uiState.value
                val pastaAtual = state.pasta ?: return
                val pastaAtualizada = pastaAtual.copy(nome = state.novoNome)

                viewModelScope.launch {
                    repository.atualizarPasta(pastaAtualizada)

                    _uiState.update {
                        it.copy(
                            pasta = pastaAtualizada,
                            mostrarDialogoMudarNome = false
                        )
                    }
                }
            }

            fun aoClicarExcluirPasta() {
                val state = _uiState.value
                val pasta = state.pasta ?: return

                viewModelScope.launch {
                    state.mostrarDialogoExcluir = false
                    repository.excluirPasta(pasta)
                }
            }

            fun aoClicarAlterarVisibilidade() {
                val pastaAtual = _uiState.value.pasta ?: return
                val pastaAtualizada = pastaAtual.copy( statusPrivado = !pastaAtual.statusPrivado)

                viewModelScope.launch {
                    repository.atualizarPasta(pastaAtualizada)

                    _uiState.update { it.copy(pasta = pastaAtualizada) }
                }
            }

            fun abrirDialogoExcluir() {
                _uiState.update {
                    it.copy(
                        novoNome = it.pasta?.nome.orEmpty(),
                        mostrarDialogoMudarNome = true
                    )
                }
            }

            fun fecharDialogoExcluir(){
                _uiState.update{
                    it.copy(mostrarDialogoExcluir = false)
                }
            }

            fun aoClicarAdicionarImagem(uri: String, largura: Int, altura: Int) {
                val idPasta = _uiState.value.pasta?.id?: return

                viewModelScope.launch {
                    val novoPinId = pinRepository.adicionarPin(uri, largura, altura)
                    repository.adicionarPinNaPasta(novoPinId, idPasta)
                }
            }

            fun aoClicarAmpliarImagem(pin: Pin) {
                _uiState.update { it.copy(imagemAmpliada = pin) }
            }

            fun fecharImagemAmpliada() {
                _uiState.update { it.copy(imagemAmpliada = null) }
            }

            fun aoClicarExcluirPin(pin: Pin) {
                val state = _uiState.value
                val pastaId = state.pasta?.id ?: return

                viewModelScope.launch {
                    pinRepository.excluirPinDaPasta(pin.id, pastaId)

                }
            }

            fun onTituloChange(titulo: String) {
                _uiState.update { it.copy(novoTitulo = titulo) }
            }

            fun abrirEdicaoTitulo() {
                _uiState.update {
                    it.copy(
                        novoTitulo = it.imagemAmpliada?.titulo.orEmpty(),
                        editandoPin = true
                    )
                }
            }

            fun aoClicarSalvarTitulo() {
                val state = _uiState.value
                val pin = state.imagemAmpliada?: return
                val pinAtualizado = pin.copy(titulo = state.novoTitulo)

                viewModelScope.launch {
                    pinRepository.alterarTituloPin(pinAtualizado)
                    _uiState.update {
                        it.copy(imagemAmpliada = pinAtualizado)
                    }
                }
            }

            fun fecharEdicaoTitulo() {
                _uiState.update { it.copy(editandoPin = false) }
            }

}