package java.androidatc.thetodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDate

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo where id=:id")
    suspend fun getTodobyId(id: Int): Todo?

    @Query("SELECT * FROM todo where category = :category and isDone = false ORDER BY todoDate,priority ")
    fun getTodobyCategory(category: String): LiveData<List<Todo>>

    @Query("SELECT * FROM todo where isDone = :status ORDER BY todoDate,priority")
    fun getIsDoneTodo(status:Boolean): LiveData<List<Todo>>

    @Query("SELECT * FROM todo ORDER BY todoDate,priority")
    fun getTodos():LiveData< List<Todo>>

    @Query("SELECT * FROM todo WHERE todoDate = :today ORDER BY priority")
    fun getTasksForToday(today: LocalDate): LiveData<List<Todo>>


    @Query("SELECT * FROM todo WHERE isDone = false ORDER BY todoDate")
    fun getdateOrderTodos():LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE  isDone = false ORDER BY priority,todoDate")
    fun getpriorityOrderTodos():LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE Date(todoDate) < Date('now') AND isDone=false ORDER BY priority,todoDate")
    fun getLateTodos():LiveData<List<Todo>>




}