package com.example.myapplication.data.repository

import com.example.myapplication.PastaDAO
import com.example.myapplication.PastaPin
import com.example.myapplication.Pin
import com.example.myapplication.data.Pasta
import kotlinx.coroutines.flow.Flow

class PastaRepository(
    private val pastaDAO: PastaDAO,
) {
     fun buscarPastas(usuarioId: Int): Flow<List<Pasta>> =
         pastaDAO.pastasDoUsuario(usuarioId)

     suspend fun buscarPastaId(pastaId: Int): Pasta =
         pastaDAO.buscarPorId(pastaId)

     fun buscarPinsPasta(pastaId: Int) : Flow<List<Pin>> =
         pastaDAO.pinsDaPasta(pastaId)

    suspend fun contarPinsPasta(pastaId: Int): Int =
        pastaDAO.contarPins(pastaId)

    fun buscarDoisUltimosPins(idPasta: Int): Flow<List<Pin>> =
        pastaDAO.doisUltimosPins(idPasta)

    suspend fun criarPasta(nome: String, privado: Boolean, usuarioId: Int) =
        pastaDAO.criar(Pasta(nome = nome, statusPrivado = privado, usuarioId = usuarioId))

    suspend fun excluirPasta(pasta: Pasta) {
        pastaDAO.excluirLigacao(pasta.id)
        pastaDAO.excluir(pasta)
    }

    suspend fun atualizarPasta( pasta: Pasta) =
        pastaDAO.atualizar(pasta)

    suspend fun adicionarPinNaPasta(pinId: Int, pastaId: Int) =
        pastaDAO.vincular(PastaPin(pastaId, pinId))

    }


