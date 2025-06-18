@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Task(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)

@Composable
fun TaskManagerScreen() {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Hello, Alex",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Estas son tus tareas",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tasks List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskChecked = { taskId, isChecked ->
                        tasks = tasks.map {
                            if (it.id == taskId) it.copy(isCompleted = isChecked)
                            else it
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add Task Button
        Button(
            onClick = {
                // Acción para agregar nueva tarea
                val newTask = Task(
                    id = tasks.size + 1,
                    title = "Nueva Tarea ${tasks.size + 1}"
                )
                tasks = tasks + newTask
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE3F2FD)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Task",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Task",
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskChecked: (Int, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isChecked ->
                onTaskChecked(task.id, isChecked)
            },
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Gray,
                checkedColor = Color(0xFF1976D2)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskManagerScreenPreview() {
    TaskManagerScreen()
}