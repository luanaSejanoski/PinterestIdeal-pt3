package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.myapplication.data.Pasta

@Dao
interface PastaDAO {
    @Insert suspend fun criar(pasta: Pasta) : Long
    @Insert suspend fun vincular(ligacao: PastaPin)


    @Query("SELECT * FROM Pasta WHERE usuarioId = :usuarioId")
    suspend fun pastasDoUsuario(usuarioId: Int): List<Pasta>

    @Query("SELECT COUNT(*) FROM PastaPin WHERE pastaId = :pastaId")
    suspend fun contarPins(pastaId: Int): Int
}