package com.example.myapplication.data.repository

import com.example.myapplication.Mensagem
import com.example.myapplication.MensagemDAO
import kotlinx.coroutines.flow.Flow

class MensagemRepository(
    private val mensagemDAO: MensagemDAO
) {
    fun buscarConversa(usuarioId: Int, amigoId: Int): Flow<List<Mensagem>> =
        mensagemDAO.buscar(usuarioId, amigoId)
}