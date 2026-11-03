package com.example.cardsdi

import com.example.cardsdi.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun myEJbuttoncheck() {

    var mensaje by remember { mutableStateOf("") }
    var activar by remember { mutableStateOf(false) }
    var mostrarOpciones by remember { mutableStateOf(false) }
    var opcion by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    var numeroImagen by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    val imagenes = listOf(
        R.drawable.imagen1,
        R.drawable.imagen2,
        R.drawable.imagen3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 4. Icono que siempre se muestra
        Text(text = "★", style = MaterialTheme.typography.headlineLarge)

        // 1. Botón
        Button(
            onClick = {
                mensaje = "Botón presionado"
                numeroImagen = (numeroImagen + 1) % imagenes.size

                scope.launch {
                    cargando = true
                    delay(5000)
                    cargando = false
                }
            }
        ) {
            Text("Presionar")
        }

        // Indicador de carga durante 5 segundos
        if (cargando) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3. Checkbox
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = activar,
                onCheckedChange = { activar = it }
            )
            Text("Activar")
        }

        // 2. Campo de texto oculto al principio
        if (activar) {
            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text("Mensaje") }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 5. Switch
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mostrar opciones")
            Switch(
                checked = mostrarOpciones,
                onCheckedChange = { mostrarOpciones = it }
            )
        }

        // 6. Botones de radio
        if (mostrarOpciones) {
            listOf("Opción 1", "Opción 2", "Opción 3").forEach { texto ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = opcion == texto,
                        onClick = {
                            opcion = texto
                            mensaje = texto
                        }
                    )
                    Text(texto)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 7. Imagen que cambia al pulsar el botón
        Image(
            painter = painterResource(id = imagenes[numeroImagen]),
            contentDescription = "Mi imagen",
            modifier = Modifier.size(150.dp)
        )
    }
}