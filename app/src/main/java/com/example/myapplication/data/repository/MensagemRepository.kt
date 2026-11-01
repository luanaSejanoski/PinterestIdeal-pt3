package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Mensagem
import com.example.myapplication.data.local.MensagemDAO
import com.example.myapplication.data.local.UsuarioDAO
import kotlinx.coroutines.flow.Flow

class MensagemRepository(
    private val mensagemDAO: MensagemDAO,
    private val usuarioDAO: UsuarioDAO
) {
   suspend fun inserir(mensagem: Mensagem) =
       mensagemDAO.inserir(mensagem)
    fun buscarConversa(usuarioId: Int, amigoId: Int): Flow<List<Mensagem>> =
        mensagemDAO.buscar(usuarioId, amigoId)
}