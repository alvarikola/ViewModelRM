package com.example.viewmodelrm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Marcador::class, TipoMarcador::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marcadorDao(): MarcadorDao
    abstract fun tipoMarcadorDao(): TipoMarcadorDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marcadores_database" ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}