package java.androidatc.thetodoapp.data

import androidx.lifecycle.LiveData
import java.time.LocalDate

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodobyId(id: Int): Todo? {
        return dao.getTodobyId(id)
    }

    override fun getTodobyCategory(category: String): LiveData<List<Todo>> {
        return dao.getTodobyCategory(category)
    }

    override fun getIsDoneTodo(status: Boolean): LiveData<List<Todo>> {
        return dao.getIsDoneTodo(status)
    }

    override fun getTodos(): LiveData<List<Todo>> {
        return dao.getTodos()
    }

    override fun getTasksForToday(date : LocalDate): LiveData<List<Todo>> {
        return dao.getTasksForToday(date)
    }

    override fun getdateOrderTodos(): LiveData<List<Todo>> {
        return dao.getdateOrderTodos()
    }

    override fun getpriorityOrderTodos(): LiveData<List<Todo>> {
        return dao.getpriorityOrderTodos()
    }

    override fun getLateTodos(): LiveData<List<Todo>> {
        return dao.getLateTodos()
    }

}