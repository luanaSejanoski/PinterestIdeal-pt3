package com.example.myapplication.data.repository

import com.example.myapplication.PastaDAO
import com.example.myapplication.Pin
import com.example.myapplication.PinDAO

class PinRepository(
    private val pinDAO: PinDAO,
    private val pastaDAO: PastaDAO
) {

    suspend fun adicionarPin(uri: String, largura: Int, altura: Int): Int =
        pinDAO.adicionar(Pin(imagem = uri, largura = largura, altura = altura)).toInt()

    suspend fun excluirPinDaPasta(pinId: Int, pastaId: Int) =
        pastaDAO.excluirPinPasta(pastaId, pinId)

    suspend fun alterarTituloPin(novoTitulo: String, pin: Pin) {
            pin.titulo = novoTitulo
            pinDAO.editar(pin)
        }
}