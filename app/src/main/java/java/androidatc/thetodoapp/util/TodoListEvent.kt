package java.androidatc.thetodoapp.util

import java.androidatc.thetodoapp.data.Todo


sealed class TodoListEvent {

    data class OnDeleteClick(val todo: Todo) : TodoListEvent()
    data class OnCheck(val todo: Todo, val isDone: Boolean) : TodoListEvent()
}

