package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update


@Dao
interface PinDAO {
    @Insert suspend fun adicionar(pin: Pin) : Long

    @Query("SELECT * FROM Pin WHERE id NOT IN (SELECT pinId FROM PastaPin)")
    suspend fun pinsSemPasta(): List<Pin>

    @Delete
    suspend fun excluirPin(pins: List<Pin>)

    @Update
    suspend fun editar(pin: Pin)

    @Query("SELECT COUNT(*) FROM Pin")
    suspend fun contarPins(): Int
}