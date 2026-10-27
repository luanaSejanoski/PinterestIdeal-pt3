package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface UsuarioDAO {
    @Insert suspend fun inserir(usuario: Usuario): Long
    @Insert suspend fun vincular(ligacao: UsuarioAmigo)

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun buscarUsuarioId(id: Int): Usuario

    @Query("SELECT COUNT(*) FROM Usuario")
    suspend fun contarUsuarios(): Int

    @Query("SELECT COUNT(*) FROM UsuarioAmigo")
    suspend fun contarAmizades(): Int

    @Query(" SELECT * FROM Usuario WHERE id > 0")
    suspend fun listarTodosUsuarios() : List<Usuario>

    @Update
    suspend fun atualizarUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    suspend fun buscarTodosUsuarios(): List<Usuario>

    @Insert
    suspend fun adicionarAmigo(usuarioAmigo: UsuarioAmigo)

    @Query("SELECT  COUNT(DISTINCT amigoId) FROM UsuarioAmigo WHERE usuarioId = :idUsuario")
    suspend fun contarAmigos(idUsuario: Int) : Int

    @Query("""
    SELECT u.* FROM Usuario u
    INNER JOIN UsuarioAmigo ua ON ua.amigoId = u.id
    WHERE ua.usuarioId = :idUsuario
    """)
    suspend fun buscarAmigos(idUsuario: Int): List<Usuario>

    @Query("""
    DELETE FROM UsuarioAmigo
    WHERE (usuarioId = :idUsuario AND amigoId = :idAmigo)
       OR (usuarioId = :idAmigo AND amigoId = :idUsuario)
    """)
    suspend fun removerAmigo(idUsuario: Int, idAmigo: Int)
}