package com.example.myapplication.data.repository

import com.example.myapplication.data.local.PastaDAO
import com.example.myapplication.data.local.Pin
import com.example.myapplication.data.local.PinDAO

class PinRepository(
    private val pinDAO: PinDAO,
    private val pastaDAO: PastaDAO
) {

    suspend fun adicionarPin(uri: String, largura: Int, altura: Int): Int =
        pinDAO.adicionar(Pin(imagem = uri, largura = largura, altura = altura)).toInt()

    suspend fun excluirPinDaPasta(pinId: Int, pastaId: Int) =
        pastaDAO.excluirPinPasta(pastaId, pinId)

    suspend fun alterarTituloPin( pin: Pin) {
            pinDAO.editar(pin)
        }
}