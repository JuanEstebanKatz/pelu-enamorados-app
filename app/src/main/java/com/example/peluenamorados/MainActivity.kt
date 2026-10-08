package com.example.peluenamorados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peluenamorados.ui.theme.PeluEnamoradosTheme

import coil.compose.AsyncImage
import androidx.activity.result.contract.ActivityResultContracts
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeluEnamoradosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaMascotas(
                        mascotas = mascotasDePrueba,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaMascotas(mascotas: List<Mascota>, modifier: Modifier = Modifier) {
    var indiceActual by remember { mutableStateOf(0) }
    val ubicacionUsuario by obtenerUbicacion()
    if (indiceActual >= mascotas.size) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("¡No hay más mascotas por ahora! 🐾")
        }
        return
    }

    val mascota = mascotas[indiceActual]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = mascota.imagenUrl,
                    contentDescription = "Foto de ${mascota.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(mascota.nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("${mascota.raza} . ${mascota.edad} años")
                Spacer(modifier = Modifier.height(8.dp))
                Text(mascota.descripcion)
                Spacer(modifier = Modifier.height(8.dp))
                if (ubicacionUsuario.lat != null && ubicacionUsuario.lng != null) {
                    val distancia = calcularDistanciaKm(
                        ubicacionUsuario.lat!!, ubicacionUsuario.lng!!,
                        mascota.latitud, mascota.longitud
                    )
                    Text("📍 A ${"%.1f".format(distancia)} km de ti", fontSize = 14.sp)
                } else {
                    Text("📍 Calculando distancia...", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Button(onClick = { indiceActual++ }) {
                Text("❌ Dislike")
            }
            Button(onClick = { indiceActual++ }) {
                Text("❤️ Like")
            }
        }
    }
}