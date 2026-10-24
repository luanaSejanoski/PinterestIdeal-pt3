package com.example.myapplication

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.myapplication.data.Pasta

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