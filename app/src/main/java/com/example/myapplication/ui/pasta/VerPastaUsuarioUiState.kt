package com.example.myapplication.ui.pasta

import com.example.myapplication.data.local.Pin
import com.example.myapplication.data.local.Pasta

data class VerPastaUsuarioUiState(
    val pasta: Pasta? = null,
    val pins: List<Pin> = emptyList(),
    val totalPinsPasta: Int = 0,
    val imagemAmpliada: Pin? = null,
    val nomePasta: String = ""
){
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