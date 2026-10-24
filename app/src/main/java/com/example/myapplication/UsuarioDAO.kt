package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface UsuarioDAO {
    @Insert suspend fun inserir(usuario: Usuario): Long
    @Insert suspend fun vincular(ligacao: UsuarioAmigo)

    @Query("SELECT COUNT(*) FROM Usuario")
    suspend fun contarUsuarios(): Int

    @Query(" SELECT * FROM Usuario WHERE id > 0")
    suspend fun listarTodosUsuarios() : List<Usuario>

    @Query("SELECT COUNT(*) FROM UsuarioAmigo WHERE usuarioId = :idUsuario")
    suspend fun contarAmigos(idUsuario: Int) : Int
}