
package com.example.cardsdi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

data class Tarea(
    val id: Int,
    val nombre: String,
    val hecha: Boolean,
    val prioridad: String
)

@Composable
fun myCards() {

    var tareas by remember {
        mutableStateOf(
            listOf(
                Tarea(1, "Estudiar", false, "Alta"),
                Tarea(2, "Hacer deberes", false, "Media"),
                Tarea(3, "Leer apuntes", true, "Baja"),
                Tarea(4, "Terminar proyecto", false, "Baja")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            "Mis tareas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Pendientes")

        Spacer(modifier = Modifier.height(8.dp))

        tareas.filter { !it.hecha }.forEach { tarea ->

            TarjetaSimple(tarea, tareas) { nuevaLista ->
                tareas = nuevaLista
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(thickness = 2.dp)

        Spacer(modifier = Modifier.height(10.dp))

        Text("Completadas")

        tareas.filter { it.hecha }.forEach { tarea ->

            TarjetaSimple(tarea, tareas) { nuevaLista ->
                tareas = nuevaLista
            }

        }
    }
}

@Composable
fun TarjetaSimple(
    tarea: Tarea,
    tareas: List<Tarea>,
    actualizar: (List<Tarea>) -> Unit
) {

    var menu1 by remember { mutableStateOf(false) }
    var menu2 by remember { mutableStateOf(false) }

    val color = when (tarea.prioridad) {
        "Alta" -> Color.Red
        "Media" -> Color(0xFFFF9800)
        else -> Color.Green
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(tarea.nombre)

            Spacer(modifier = Modifier.height(8.dp))

            // Badge de prioridad
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Prioridad: ")

                Spacer(modifier = Modifier.width(8.dp))

                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = color
                        ) {
                            Text(tarea.prioridad)
                        }
                    }
                ) {
                    // Espacio para que el badge no tape el texto
                    Spacer(modifier = Modifier.size(48.dp, 24.dp))
                }
            }

            // Primer menú: cambiar estado
            Box {

                TextButton(
                    onClick = { menu1 = true }
                ) {
                    Text("Estado")
                }

                DropdownMenu(
                    expanded = menu1,
                    onDismissRequest = { menu1 = false }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text(
                                if (tarea.hecha) {
                                    "Marcar pendiente"
                                } else {
                                    "Marcar completada"
                                }
                            )
                        },
                        onClick = {

                            actualizar(
                                tareas.map {
                                    if (it.id == tarea.id) {
                                        it.copy(hecha = !it.hecha)
                                    } else {
                                        it
                                    }
                                }
                            )

                            menu1 = false
                        }
                    )
                }
            }

            // Segundo menú: cambiar prioridad
            Box {

                TextButton(
                    onClick = { menu2 = true }
                ) {
                    Text("Cambiar prioridad")
                }

                DropdownMenu(
                    expanded = menu2,
                    onDismissRequest = { menu2 = false }
                ) {

                    listOf("Alta", "Media", "Baja").forEach { p ->

                        DropdownMenuItem(
                            text = {
                                Text(p)
                            },
                            onClick = {

                                actualizar(
                                    tareas.map {
                                        if (it.id == tarea.id) {
                                            it.copy(prioridad = p)
                                        } else {
                                            it
                                        }
                                    }
                                )

                                menu2 = false
                            }
                        )
                    }
                }
            }
        }
    }
}

