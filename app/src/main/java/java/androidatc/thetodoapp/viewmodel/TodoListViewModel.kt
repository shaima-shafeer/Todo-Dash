package java.androidatc.thetodoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.data.TodoRepository

import java.androidatc.thetodoapp.util.TodoListEvent
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    private var deletedTodo: Todo? = null

    var todos = repository.getTodos()
    fun show(selected: String?) {
        viewModelScope.launch {
            todos =
                when (selected) {
                    "PROFESSIONAL" -> repository.getTodobyCategory("PROFESSIONAL")
                    "PERSONAL" -> repository.getTodobyCategory("PERSONAL")
                    "HEALTH" -> repository.getTodobyCategory("HEALTH")
                    "OTHERS" -> repository.getTodobyCategory("OTHERS")
                    "DONE" -> repository.getIsDoneTodo(true)
                    "NOT DONE" -> repository.getIsDoneTodo(false)
                    "DATE" -> repository.getdateOrderTodos()
                    "PRIORITY" -> repository.getpriorityOrderTodos()
                    "LATE" -> repository.getLateTodos()
                    else -> repository.getTodos()

                }
            if (selected != null) {
                Log.d("Late", selected)
            }
        }
    }


    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnCheck -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

            is TodoListEvent.OnDeleteClick -> {
                deletedTodo = event.todo
                viewModelScope.launch {
                    repository.deleteTodo(event.todo)

                }
            }

        }
    }
}