package com.example.viewmodelrm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodelrm.data.MarcadorDao
import com.example.viewmodelrm.data.MarcadorWithTipo
import kotlinx.coroutines.flow.Flow

class MarcadorViewModel(private val marcadorDao: MarcadorDao) : ViewModel() {

    val marcadoresWithTipo: Flow<List<MarcadorWithTipo>> = marcadorDao.getAllMarcadoresAndTipos()
}