package java.androidatc.thetodoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.data.TodoRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    val today = LocalDate.now()
    val alltodo: LiveData<List<Todo>> = repository.getIsDoneTodo(false)
    val todaytodo: LiveData<List<Todo>> = repository.getTasksForToday(today)

}