package java.androidatc.thetodoapp.util

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String) : AddEditTodoEvent()
    data class OnDescriptionChange(val description: String) : AddEditTodoEvent()
    data class OnCategoryChange(val category: String) : AddEditTodoEvent()
    data class OnPriorityChange(val priority: Int) : AddEditTodoEvent()
    data class OnProgressChange(val progress: Float) : AddEditTodoEvent()
    object OnSaveTodoClick : AddEditTodoEvent()
    object OnClearTodoClick : AddEditTodoEvent()

}