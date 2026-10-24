package com.example.myapplication

import android.util.Log
import com.example.myapplication.data.Pasta

suspend fun buscarPastas(pastaDAO: PastaDAO, usuarioId: Int): List<Pasta> {
    return try {
        pastaDAO.pastasDoUsuario(usuarioId)
    } catch (e: Exception) {
        Log.e("Erro ao inserir...", "Msg ${e.message}")
        emptyList()
    }
}