package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.myapplication.data.Pasta

@Dao
interface PastaDAO {
    @Insert suspend fun criar(pasta: Pasta)

    @Insert suspend fun vincular(ligacao: PastaPin)

    @Query("SELECT * FROM Pasta WHERE usuarioId = :usuarioId")
    suspend fun pastasDoUsuario(usuarioId: Int): List<Pasta>

    //Busca todos os pins que pertencem a pasta selecionada
    @Query("""
        SELECT Pin.* FROM Pin
        INNER JOIN PastaPin ON Pin.id = PastaPin.pinId
        WHERE PastaPin.pastaId = :pastaId
        ORDER BY Pin.id DESC
        """)
    suspend fun pinsDaPasta(pastaId: Int): List<Pin>

    @Query("SELECT COUNT(*) FROM PastaPin WHERE pastaId = :pastaId")
    suspend fun contarPins(pastaId: Int): Int

    @Query("""SELECT Pin.* FROM Pin
        INNER JOIN PastaPin ON Pin.id = PastaPin.pinId
        WHERE PastaPin.pastaId = :idPasta
        ORDER BY Pin.id DESC
        LIMIT 2
        """)
    suspend fun doisUltimosPins(idPasta: Int): List<Pin>

    @Delete suspend fun excluir(pasta: Pasta)

    @Query("DELETE FROM PastaPin WHERE pastaId = :idPasta")
    suspend fun excluirLigacao(idPasta: Int)

    @Query("UPDATE Pasta SET nome = :novoNome WHERE id = :idPasta")
    suspend fun mudarNome(novoNome: String, idPasta: Int)

    @Query("DELETE FROM PastaPin WHERE pastaId = :idPasta AND pinId = :idPin")
    suspend fun excluirPinPasta(idPasta: Int, idPin: Int)

    @Query("SELECT COUNT(*) FROM PastaPin WHERE pinId = :idPin")
    suspend fun contarPastasPin(idPin: Int): Int


}