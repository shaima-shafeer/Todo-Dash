package java.androidatc.thetodoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.data.TodoRepository
import javax.inject.Inject
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.androidatc.thetodoapp.util.AddEditTodoEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository

) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var progress by mutableFloatStateOf(0f)
    var category by mutableStateOf("OTHERS")
    var priority by mutableIntStateOf(0)
    var date by mutableStateOf(LocalDate.now())
    var todo by mutableStateOf<Todo?>(null)

    var todoId = -1
    fun display() {
        Log.d("todoId", todoId.toString())
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodobyId(todoId)?.let { todo ->
                    date = todo.todoDate
                    title = todo.title
                    description = todo.description ?: ""
                    progress = todo.progress
                    category = todo.category
                    priority = todo.priority
                    this@AddEditTodoViewModel.todo = todo

                }

            }

        } else {
            clearTodo()
        }


    }


    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnCategoryChange -> {
                category = event.category
            }

            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }

            is AddEditTodoEvent.OnPriorityChange -> {
                priority = event.priority
            }

            is AddEditTodoEvent.OnProgressChange -> {
                progress = event.progress
            }

            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }

            AddEditTodoEvent.OnSaveTodoClick -> {
                saveTodo()

            }

            AddEditTodoEvent.OnClearTodoClick -> {
                clearTodo()
            }

        }
    }

    fun saveTodo() {


        viewModelScope.launch(Dispatchers.IO) {

            repository.insertTodo(
                Todo(
                    todoDate = date,
                    title = title,
                    description = description,
                    category = category,
                    priority = priority,
                    isDone = false,
                    progress = progress,
                    id = todo?.id
                )
            )
        }
    }

    fun clearTodo() {
        date = LocalDate.now()
        title = ""
        description = ""
        progress = 0f
        category = "OTHERS"
        priority = 0
    }

}