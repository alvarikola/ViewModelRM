package com.example.viewmodelrm.data

import androidx.room.Embedded
import androidx.room.Relation

data class MarcadorWithTipo(
    @Embedded val marcador: Marcador,
    @Relation(
        parentColumn = "idTipoMarcadorOwner",
        entityColumn = "idTipoMarcador"
    )
    val tiposMarcadores: List<TipoMarcador>
)