package com.example.viewmodelrm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



@Dao
interface MarcadorDao {

    @Insert
    suspend fun insertMarcador(marcador: Marcador)


    @Query("SELECT * FROM MARCADORES")
    suspend fun getAllMarcadoresAndTipos(): List<MarcadorWithTipo>
}
