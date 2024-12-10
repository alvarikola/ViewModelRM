package com.example.viewmodelrm.view

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.viewmodelrm.R
import com.example.viewmodelrm.viewModel.MarcadorViewModel
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import kotlinx.coroutines.flow.first
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex


// Google Satellite Tile Source
val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com"
    )
) {
    override fun getTileURLString(aTile: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(aTile) + "&y=" + MapTileIndex.getY(
            aTile
        ) + "&z=" + MapTileIndex.getZoom(aTile)
    }
}

@Composable
fun MyMapView(modifier: Modifier = Modifier, viewModel: MarcadorViewModel) {

    val marcadoresWithTipo by viewModel.marcadoresWithTipo.collectAsState(initial = emptyList())


    // define camera state
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.957375205489004, -13.554245657440829)
        zoom = 17.0 // optional, default is 5.0
    }

    // define properties with remember with default value
    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    // setup mapProperties in side effect
    SideEffect {
        mapProperties = mapProperties
            //.copy(isTilesScaledToDpi = true)
            //.copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(tileSources = GoogleSat)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties // add properties
    ) {

        // Dibujar los marcadores en el mapa
        marcadoresWithTipo.forEach { marcadorWithTipo ->
            // Para cada marcador, usamos sus coordenadas y tipo
            val marcador = marcadorWithTipo.marcador
            val tipo = marcadorWithTipo.tiposMarcadores[0].tituloTipoMarcador
            var icono by remember { mutableStateOf(R.drawable.ic_launcher_background) }

            when (marcadorWithTipo.tiposMarcadores[0].idTipoMarcador) {
                0 -> icono = R.drawable.icono_deporte
                1 -> icono = R.drawable.icono_educacion
                2 -> icono = R.drawable.icono_viaje
                3 -> icono = R.drawable.icono_viaje
            }

//            if (marcadorWithTipo.tiposMarcadores[0].idTipoMarcador == 0) {
//                icono = ContextCompat.getDrawable(LocalContext.current, R.drawable.icono_deporte)
//            }
//            if (marcadorWithTipo.tiposMarcadores[0].idTipoMarcador == 1) {
//                icono = ContextCompat.getDrawable(LocalContext.current, R.drawable.icono_deporte)
//            }
//            if (marcadorWithTipo.tiposMarcadores[0].idTipoMarcador == 2) {
//                icono = ContextCompat.getDrawable(LocalContext.current, R.drawable.icono_viaje)
//            }
//            if (marcadorWithTipo.tiposMarcadores[0].idTipoMarcador == 3) {
//                icono = ContextCompat.getDrawable(LocalContext.current, R.drawable.icono_cultura)
//            }
            val markerState = rememberMarkerState(
                geoPoint = GeoPoint(marcador.coordenadaX, marcador.coordenadaY)
            )

            Marker(
                state = markerState,
                title = marcador.tituloMarcador, // add title
                snippet = tipo, // add snippet
                icon = ContextCompat.getDrawable(LocalContext.current, icono)
            ) {

                // create info window node
                Column(
                    modifier = Modifier
                        .size(150.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // setup content of info window
                    Text(text = it.title, fontWeight = FontWeight.Bold) // Título del marcador
                    Text(text = it.snippet, fontSize = 12.sp) // Snippet del marcador (Tipo)
                }
            }
        }
    }
}
