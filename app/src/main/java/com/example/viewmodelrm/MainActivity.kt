package com.example.viewmodelrm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodelrm.data.AppDatabase
import com.example.viewmodelrm.ui.theme.ViewModelRMTheme
import com.example.viewmodelrm.view.MyMapView
import com.example.viewmodelrm.viewModel.MarcadorViewModel
import com.example.viewmodelrm.viewModel.ViewModelFactory




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val marcadorDao = database.marcadorDao()
        enableEdgeToEdge()
        setContent {
            ViewModelRMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel: MarcadorViewModel = viewModel(factory = ViewModelFactory(marcadorDao))


                    MyMapView(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}