package com.example.viewmodelrm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [Marcador::class, TipoMarcador::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marcadorDao(): MarcadorDao
    abstract fun tipoMarcadorDao(): TipoMarcadorDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        @OptIn(DelicateCoroutinesApi::class)
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marcadores_database" ).build()
                INSTANCE = instance

                // Insertar los 4 tipos de marcadores si la base de datos está vacía
                GlobalScope.launch {
                    datosIniciales(instance.tipoMarcadorDao(), instance.marcadorDao())
                }

                instance
            }
        }
        // Función para insertar los tipos y marcadores iniciales
        private suspend fun datosIniciales(tipoMarcadorDao: TipoMarcadorDao, marcadorDao: MarcadorDao) {
            // Insertar tipos
            val tipos = listOf(
                TipoMarcador(tituloTipoMarcador = "Deportivo"),
                TipoMarcador(tituloTipoMarcador = "Educativo"),
                TipoMarcador(tituloTipoMarcador = "Turístico"),
                TipoMarcador(tituloTipoMarcador = "Cultural")
            )

            val tiposFromDb = tipoMarcadorDao.getAllTipos().first()

            // Si no existen tipos en la base de datos insertamos los tipos iniciales
            if (tiposFromDb.isEmpty()) {
                tipos.forEach {
                    tipoMarcadorDao.insertTipoMarcador(it)
                }
            }

            // Insertar marcadores
            val tiposFromDbUpdated = tipoMarcadorDao.getAllTipos().first()

            val marcadores = listOf(
                Marcador(tituloMarcador = "Ciudad Deportiva de Lanzarote", coordenadaX = 28.967902090837306, coordenadaY = -13.555125199766206, idTipoMarcadorOwner = tiposFromDbUpdated[0].idTipoMarcador),
                Marcador(tituloMarcador = "Centro Comercial Lanzarote Open Mall", coordenadaX = 28.96827755270507, coordenadaY = -13.547014200109558, idTipoMarcadorOwner = tiposFromDbUpdated[2].idTipoMarcador),
                Marcador(tituloMarcador = "Charco de San Ginés", coordenadaX = 28.96225419122397, coordenadaY = -13.548999167187004, idTipoMarcadorOwner = tiposFromDbUpdated[2].idTipoMarcador),
                Marcador(tituloMarcador = "Castillo de San Gabriel", coordenadaX = 28.956791704622184, coordenadaY = -13.54768197864985, idTipoMarcadorOwner = tiposFromDbUpdated[3].idTipoMarcador),
                Marcador(tituloMarcador = "Centro Comercial Deiland", coordenadaX = 28.95748112372263, coordenadaY = -13.586388703934691, idTipoMarcadorOwner = tiposFromDbUpdated[2].idTipoMarcador),
                Marcador(tituloMarcador = "IES Las Maretas", coordenadaX = 28.966357113655544, coordenadaY = -13.563854409304424, idTipoMarcadorOwner = tiposFromDbUpdated[1].idTipoMarcador),
                Marcador(tituloMarcador = "IES de Haría", coordenadaX = 29.142353906687323, coordenadaY = -13.506819889757423, idTipoMarcadorOwner = tiposFromDbUpdated[1].idTipoMarcador),
                Marcador(tituloMarcador = "Jameos del Agua", coordenadaX = 29.157789041252204, coordenadaY = -13.432234663241811, idTipoMarcadorOwner = tiposFromDbUpdated[3].idTipoMarcador),
                Marcador(tituloMarcador = "Parque Deportivo Municipal de Arrecife", coordenadaX = 28.96942009920041, coordenadaY = -13.547506378465872, idTipoMarcadorOwner = tiposFromDbUpdated[0].idTipoMarcador),
                Marcador(tituloMarcador = "Playa Grande", coordenadaX = 28.920977300400008, coordenadaY = -13.658290902185122, idTipoMarcadorOwner = tiposFromDbUpdated[2].idTipoMarcador),
                Marcador(tituloMarcador = "Hotel Fariones", coordenadaX = 28.920476555434167, coordenadaY = -13.666161064317063, idTipoMarcadorOwner = tiposFromDbUpdated[2].idTipoMarcador),
                Marcador(tituloMarcador = "Areafit Arrecife", coordenadaX = 28.965880963476955, coordenadaY = -13.553236950019679, idTipoMarcadorOwner = tiposFromDbUpdated[0].idTipoMarcador),

                )

            marcadores.forEach {
                marcadorDao.insertMarcador(it)
            }
        }
    }
}