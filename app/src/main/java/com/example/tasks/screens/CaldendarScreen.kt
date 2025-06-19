package com.example.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

// Modelo de datos para las tareas
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val time: LocalTime,
    val date: LocalDate,
    val isCompleted: Boolean = false
)

@Preview
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // Estado para las tareas - En producción esto vendría del ViewModel/Repository
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(
                    title = "Meeting with Alex",
                    time = LocalTime.of(10, 0),
                    date = LocalDate.of(2025, 6, 18)
                ),
                Task(
                    title = "Project Review",
                    time = LocalTime.of(14, 0),
                    date = LocalDate.of(2024, 11, 1)
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header del calendario con navegación
        CalendarHeader(
            currentMonth = currentMonth,
            onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonth = { currentMonth = currentMonth.plusMonths(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid del calendario
        CalendarGrid(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            tasksForMonth = tasks.filter { it.date.month == currentMonth.month }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fecha seleccionada y lista de tareas
        TasksSection(
            selectedDate = selectedDate,
            tasks = tasks.filter { it.date == selectedDate },
            onTaskToggle = { taskId ->
                // CONEXIÓN BACKEND: Aquí llamarías a tu repository/API
                // repository.toggleTaskCompletion(taskId)
                tasks = tasks.map { task ->
                    if (task.id == taskId) task.copy(isCompleted = !task.isCompleted)
                    else task
                }
            }
        )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Mes anterior"
            )
        }

        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es"))),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        IconButton(onClick = onNextMonth) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Mes siguiente"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    tasksForMonth: List<Task>
) {
    val daysOfWeek = listOf("D", "L", "M", "M", "J", "V", "S")
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = currentMonth.lengthOfMonth()

    Column {
        // Headers de días de la semana
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        }

        // Grid de días
        for (week in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 0..6) {
                    val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 1

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (dayNumber in 1..daysInMonth) {
                            val date = currentMonth.atDay(dayNumber)
                            val hasTask = tasksForMonth.any { it.date == date }
                            val isSelected = date == selectedDate
                            val isToday = date == LocalDate.now()

                            CalendarDay(
                                day = dayNumber,
                                isSelected = isSelected,
                                isToday = isToday,
                                hasTask = hasTask,
                                onClick = { onDateSelected(date) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDay(
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    hasTask: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    isToday -> MaterialTheme.colorScheme.primaryContainer
                    else -> Color.Transparent
                }
            )
            .border(
                width = if (hasTask && !isSelected) 1.dp else 0.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            color = when {
                isSelected -> Color.White
                isToday -> MaterialTheme.colorScheme.primary
                else -> Color.Black
            },
            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksSection(
    selectedDate: LocalDate,
    tasks: List<Task>,
    onTaskToggle: (String) -> Unit
) {
    Column {
        // Fecha seleccionada
        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale("es"))),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de tareas
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No hay tareas para este día",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                }
            }
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { onTaskToggle(task.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color.Gray.copy(alpha = 0.3f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de calendario pequeño (simulado con Box)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("📅", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Medium,
                    color = if (task.isCompleted) Color.Gray else Color.Black
                )
                Text(
                    text = task.time.format(DateTimeFormatter.ofPattern("h:mm a")),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Checkbox
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() }
            )
        }
    }
}

/*
COMENTARIOS PARA CONEXIÓN CON BACKEND:

1. ARQUITECTURA RECOMENDADA:
   - ViewModel: Maneja el estado de la UI y lógica de presentación
   - Repository: Abstrae las fuentes de datos (API + Base de datos local)
   - Room Database: Para cache local y funcionamiento offline
   - Retrofit/Ktor: Para llamadas a la API

2. ESTRUCTURA DE DATOS EN BACKEND:
   POST /api/tasks - Crear tarea
   GET /api/tasks?date=2024-11-01 - Obtener tareas por fecha
   PUT /api/tasks/{id} - Actualizar tarea
   DELETE /api/tasks/{id} - Eliminar tarea

3. IMPLEMENTACIÓN CON VIEWMODEL:
   class CalendarViewModel @Inject constructor(
       private val taskRepository: TaskRepository
   ) : ViewModel() {

       private val _tasks = MutableStateFlow<List<Task>>(emptyList())
       val tasks = _tasks.asStateFlow()

       fun loadTasksForMonth(yearMonth: YearMonth) {
           viewModelScope.launch {
               _tasks.value = taskRepository.getTasksForMonth(yearMonth)
           }
       }

       fun toggleTask(taskId: String) {
           viewModelScope.launch {
               taskRepository.toggleTaskCompletion(taskId)
               // Recargar tareas o actualizar estado local
           }
       }
   }

4. REPOSITORY PATTERN:
   class TaskRepository @Inject constructor(
       private val apiService: TaskApiService,
       private val taskDao: TaskDao
   ) {
       suspend fun getTasksForMonth(yearMonth: YearMonth): List<Task> {
           return try {
               val tasks = apiService.getTasksForMonth(yearMonth)
               taskDao.insertAll(tasks) // Cache local
               tasks
           } catch (e: Exception) {
               taskDao.getTasksForMonth(yearMonth) // Fallback offline
           }
       }
   }

5. MODIFICACIONES EN COMPOSE:
   - Reemplazar remember { mutableStateOf } con collectAsState() del ViewModel
   - Pasar callbacks del ViewModel en lugar de manipular estado local
   - Usar LaunchedEffect para cargar datos cuando cambie el mes

6. BASE DE DATOS LOCAL (Room):
   @Entity(tableName = "tasks")
   data class TaskEntity(
       @PrimaryKey val id: String,
       val title: String,
       val date: String, // ISO format
       val time: String, // ISO format
       val isCompleted: Boolean,
       val syncStatus: SyncStatus // Para manejo offline
   )
*/