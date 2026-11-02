package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PinDAO {
    @Insert
    suspend fun adicionar(pin: Pin) : Long

    @Delete
    suspend fun excluirPin(pins: List<Pin>)

    @Update
    suspend fun editar(pin: Pin)

    @Query("SELECT COUNT(*) FROM Pin")
    suspend fun contarPins(): Int
}