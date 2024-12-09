package com.example.viewmodelrm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Marcadores")
data class Marcador(
    @PrimaryKey(autoGenerate = true) val idMarcador: Int = 0,
    @ColumnInfo(name = "Título") val tituloMarcador: String,
    @ColumnInfo(name = "Descripción") val descripcionMarcador: String?,
    @ColumnInfo(name = "CoordenadaX") val coordenadaX: Double,
    @ColumnInfo(name = "CoordenadaY") val coordenadaY: Double,
    val idTipoMarcadorOwner: Int
)
