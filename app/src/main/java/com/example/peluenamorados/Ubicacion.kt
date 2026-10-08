package com.example.peluenamorados

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices

data class UbicacionUsuario(val lat: Double?, val lng: Double?)

@SuppressLint("MissingPermission")
@Composable
fun obtenerUbicacion(): State<UbicacionUsuario> {
    val context = LocalContext.current
    val ubicacion = remember { mutableStateOf(UbicacionUsuario(null, null)) }

    LaunchedEffect(Unit) {
        val cliente = LocationServices.getFusedLocationProviderClient(context)
        cliente.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                ubicacion.value = UbicacionUsuario(location.latitude, location.longitude)
            }
        }
    }
    return ubicacion
}