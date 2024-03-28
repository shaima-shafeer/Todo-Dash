package java.androidatc.thetodoapp.data

import androidx.lifecycle.LiveData
import java.time.LocalDate

interface TodoRepository{
    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodobyId(id: Int): Todo?

    fun getTodobyCategory(category: String): LiveData<List<Todo>>

    fun getIsDoneTodo(status:Boolean): LiveData<List<Todo>>

    fun getTodos(): LiveData<List<Todo>>

    fun getTasksForToday(date: LocalDate):LiveData<List<Todo>>

    fun getdateOrderTodos():LiveData<List<Todo>>

    fun getpriorityOrderTodos():LiveData<List<Todo>>

    fun getLateTodos():LiveData<List<Todo>>
}