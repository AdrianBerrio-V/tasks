@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun HomeScreen() {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Morning Workout"),
                Task(2, "Grocery Shopping"),
                Task(3, "Project Meeting"),
                Task(4, "Dinner with Friends"),
                Task(5,"comer comida comestible")
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
            .background(colorResource(R.color.surface_gray)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { /*ToDo navigation top task*/ }
        ){
            Text(
                text = task.title,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 3.dp)
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text = task.date.toString(),
                color = colorResource(R.color.text_secondary),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 3.dp)
            )
        }

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