package com.example.myapplication.ui.pasta

import com.example.myapplication.data.local.Pin
import com.example.myapplication.R
import com.example.myapplication.data.local.Pasta

data class ItensPastaUiState(
    val pasta: Pasta? = null,
    val pins: List<Pin> = emptyList(),
    val totalPinsPasta: Int = 0,
    val imagemAmpliada: Pin? = null,
    val nomePasta: String = "",
    val privacidadePasta: Boolean = false,
    val mostrarDialogoMudarNome: Boolean = false,
    var mostrarDialogoExcluir: Boolean = false,
    val novoNome: String = "",
    val editandoPin: Boolean = false,
    val novoTitulo: String = ""
    ){
    val iconePasta: Int
        get() = if (privacidadePasta) R.drawable.pasta_privada
        else R.drawable.pasta_publica

    val textoPrivacidade: String
        get() = if (privacidadePasta) "Pasta privada"
        else "Pasta pública"

    fun organizaImagensColuna(
        listaPins: List<Pin>,
        colunaEsquerda: MutableList<Pin>,
        colunaDireita: MutableList<Pin>
    ){
        var alturaEsquerda = 0f
        var alturaDireita = 0f


        listaPins.forEach { imagem ->
            // Decide em qual coluna colocar (menor)
            val alturaRelativa= imagem.altura.toFloat() / imagem.largura.toFloat() //quao alta a imagem é

            if(alturaEsquerda <= alturaDireita){
                colunaEsquerda.add(imagem)
                alturaEsquerda += alturaRelativa
            }else{
                colunaDireita.add(imagem)
                alturaDireita += alturaRelativa
            }
        }
    }
}
