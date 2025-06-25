// screens/TaskDetailScreen.kt
package com.example.tasks.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tasks.R
import java.time.LocalDate
import java.time.LocalTime


data class Task(
    val id: Int,
    val title: String,
    val time: LocalTime?=null,
    val date: LocalDate?=null,
    val isCompleted: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavController, taskId: Int?) {
    val task = when (taskId) {
        1 -> Task(1, "Morning Workout", LocalTime.of(7,0), LocalDate.of(2025,6,25), false)
        2 -> Task(2, "Grocery Shopping", LocalTime.of(10,0), LocalDate.of(2025,6,26), false)
        3 -> Task(3, "Project Meeting", LocalTime.of(14,0), LocalDate.of(2025,6,27), true)
        4 -> Task(4, "Dinner with Friends", LocalTime.of(19,0), LocalDate.of(2025,6,28), false)
        5 -> Task(5, "Comer comida comestible", LocalTime.of(12,0), LocalDate.of(2025,6,29), false)
        else -> null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de la Tarea") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center // Centra el contenido en la pantalla
        ) {
            if (task != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Ocupa el 90% del ancho
                        .padding(16.dp), // Espacio alrededor de la tarjeta
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Sombra para efecto de elevación
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp) // Padding dentro de la tarjeta
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp) // Espacio entre los elementos de la columna
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = colorResource(R.color.text_primary)
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp)) // Separador visual

                        TaskDetailRow(label = "Fecha:", value = task.date?.toString() ?: "N/A")
                        TaskDetailRow(label = "Hora:", value = task.time?.toString() ?: "N/A")
                        TaskDetailRow(label = "Completada:", value = if (task.isCompleted) "Sí" else "No")

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { /* ToDo editar tarea */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.primary_blue_dark),
                                contentColor = colorResource(R.color.text_primary)
                            )
                        ) {
                            Text("Editar Tarea")
                        }
                    }
                }
            } else {
                Text(text = "Tarea no encontrada", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Composable
fun TaskDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween, // Alinea el texto a los extremos
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    TaskDetailScreen(navController = rememberNavController(), taskId = 1)
}