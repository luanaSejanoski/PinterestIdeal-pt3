package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.Usuario
import com.example.myapplication.UsuarioAmigo
import com.example.myapplication.UsuarioDAO
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(
    private val usuarioDAO: UsuarioDAO
){
    suspend fun atualizarDadosUsuario(
        usuarioDAO: UsuarioDAO,
        usuario: Usuario,
        nomeExibicao: String,
        nomeUsuario: String,
        biografia: String,
        email: String,
        fotoPerfil: String?
    ) {
        val usuarioAtualizado = usuario.copy(
            nomeExibicao = nomeExibicao,
            nomeUsuario = nomeUsuario,
            biografia = biografia,
            email = email,
            foto = fotoPerfil
        )
        usuarioDAO.atualizarUsuario(usuarioAtualizado)
    }

    fun listarUsuarios(): Flow<List<Usuario>> {
        return usuarioDAO.listarTodosUsuarios()
    }

    fun buscarAmigosDoUsuario(idUsuario: Int): Flow<List<Usuario>> {
        return usuarioDAO.buscarAmigos(idUsuario)
    }
    suspend fun adicionarAmigoBanco(usuarioId: Int, amigoId: Int) {
        val relacao = UsuarioAmigo(usuarioId = usuarioId, amigoId = amigoId)
        usuarioDAO.adicionarAmigo(relacao)
    }

    suspend fun buscarUsuarioPorId(idUsuario: Int): Usuario? {
        return usuarioDAO.buscarUsuarioId(idUsuario)
    }

    suspend fun contarAmigosUsuario(usuarioId: Int): Int {
        return usuarioDAO.contarAmigos(usuarioId)
    }

    suspend fun removerAmigoUsuario(usuarioId: Int, amigoId: Int){
        usuarioDAO.removerAmigo(usuarioId, amigoId)
    }
}