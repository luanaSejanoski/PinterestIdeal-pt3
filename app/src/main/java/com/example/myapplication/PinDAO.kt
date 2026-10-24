package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface PinDAO {
    @Insert suspend fun adicionar(pin: Pin): Long

    //Busca todos os pins que pertencem a pasta selecionada
    @Query("""
        SELECT Pin.* FROM Pin
        INNER JOIN PastaPin ON Pin.id = PastaPin.pinId
        WHERE PastaPin.pastaId = :pastaId
        """)
    suspend fun buscarPinsDaPasta(pastaId: Int): List<Pin>
}