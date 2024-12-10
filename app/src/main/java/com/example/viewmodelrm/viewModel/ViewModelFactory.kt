package com.example.viewmodelrm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelrm.data.MarcadorDao

class ViewModelFactory(private val marcadorDao: MarcadorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarcadorViewModel::class.java)) {
            return MarcadorViewModel(marcadorDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}