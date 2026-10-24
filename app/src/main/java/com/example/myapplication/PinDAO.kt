package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query


@Dao
interface PinDAO {
    @Insert suspend fun adicionar(pin: Pin) : Long

    @Query("DELETE FROM Pin WHERE id NOT IN (SELECT PinId FROM PastaPin")
    suspend fun excluirPin()
}