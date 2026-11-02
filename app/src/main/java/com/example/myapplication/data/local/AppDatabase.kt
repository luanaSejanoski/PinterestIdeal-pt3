package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pasta::class, Pin::class, PastaPin::class, Usuario::class, UsuarioAmigo::class, Mensagem::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun pastaDAO(): PastaDAO
    abstract fun pinDAO(): PinDAO
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun mensagemDAO(): MensagemDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return  tempInstance
            }else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()

                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}