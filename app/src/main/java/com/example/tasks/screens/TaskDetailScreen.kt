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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate // Importa LocalDate si lo usas en Task
import java.time.LocalTime // Importa LocalTime si lo usas en Task


data class Task(
    val id: Int,
    val title: String,
    val time: LocalTime?=null,
    val date: LocalDate?=null,
    val isCompleted: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar
@Composable
fun TaskDetailScreen(navController: NavController, taskId: Int?) {
    // En una aplicación real, aquí recuperarías la tarea de una base de datos
    // o un repositorio usando el taskId. Para este ejemplo, simularemos una tarea.
    val task = when (taskId) {
        1 -> Task(1, "Morning Workout", LocalTime.of(7,0), LocalDate.of(2025,6,25), false)
        2 -> Task(2, "Grocery Shopping", LocalTime.of(10,0), LocalDate.of(2025,6,26), false)
        3 -> Task(3, "Project Meeting", LocalTime.of(14,0), LocalDate.of(2025,6,27), true)
        4 -> Task(4, "Dinner with Friends", LocalTime.of(19,0), LocalDate.of(2025,6,28), false)
        5 -> Task(5, "Comer comida comestible", LocalTime.of(12,0), LocalDate.of(2025,6,29), false)
        else -> null // Tarea no encontrada
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
        if (task != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Aplica el padding del Scaffold
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = task.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "ID: ${task.id}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Fecha: ${task.date ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Hora: ${task.time ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Completada: ${if (task.isCompleted) "Sí" else "No"}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(32.dp))
                // Puedes añadir botones para editar o eliminar aquí
                Button(onClick = { /* Lógica para editar la tarea */ }) {
                    Text("Editar Tarea")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tarea no encontrada", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    // Para el preview, necesitas un NavController dummy y un taskId
    TaskDetailScreen(navController = rememberNavController(), taskId = 1)
}