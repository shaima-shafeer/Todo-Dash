package java.androidatc.thetodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName="todo")
data class Todo(
    val todoDate: LocalDate,
    val title:String,
    val description:String?,
    val category:String,
    val priority:Int,
    val progress:Float,
    val isDone:Boolean,
    @PrimaryKey(autoGenerate=true) val id:Int? = null
)