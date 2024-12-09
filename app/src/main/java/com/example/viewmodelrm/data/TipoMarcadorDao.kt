package com.example.viewmodelrm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TipoMarcadorDao {

    @Insert
    suspend fun insertTipoMarcador(tipoMarcador: TipoMarcador)


    @Query("SELECT * FROM TiposMarcadores")
    suspend fun getAllTipos(): Flow<List<TipoMarcador>>

}