@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun HomeScreen() {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends"),
                Task(5,"comer comida comestible"),
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends"),
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends"),
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends"),
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Hola, Alex",
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

        Spacer(Modifier.height(10.dp))
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(15.dp),
        contentAlignment = Alignment.BottomEnd
    ){
        // Add Task Button
        ExtendedFloatingActionButton(
            onClick = {/*ToDo Navegacion a añadir tarea*/},
            icon = {Icon(Icons.Filled.Add, "Agregar tarea")},
            text = { Text(text = "Agg Tarea")},
            containerColor = Color(0xFFC7DEF8)
        )
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
            .padding(vertical = 3.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFD3D3D3)),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            text = task.title,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp)
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
    HomeScreen()
}