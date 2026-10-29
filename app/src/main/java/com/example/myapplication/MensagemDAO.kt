package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MensagemDAO {
    @Insert suspend fun inserir(mensagem: Mensagem) : Long

    @Query("""
        SELECT * FROM Mensagem 
        WHERE (remetenteId = :r AND destinatarioId = :d)
        OR (remetenteId = :d AND destinatarioId = :r)
        ORDER BY id
        """)
    fun buscar(r: Int, d: Int): Flow<List<Mensagem>>
}