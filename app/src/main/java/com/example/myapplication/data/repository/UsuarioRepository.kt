package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.local.UsuarioAmigo
import com.example.myapplication.data.local.UsuarioDAO
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(
    private val usuarioDAO: UsuarioDAO
){

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

     fun contarAmigosUsuario(usuarioId: Int): Flow<Int> {
        return usuarioDAO.contarAmigos(usuarioId)
    }

    suspend fun removerAmigoUsuario(usuarioId: Int, amigoId: Int){
        usuarioDAO.removerAmigo(usuarioId, amigoId)
    }

    suspend fun atualizarDadosUsuario(
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
}