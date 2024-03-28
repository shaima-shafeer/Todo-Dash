package java.androidatc.thetodoapp.util

sealed class Screen(val route: String) {
    object HomeScreen : Screen("HomeScreen")
    object AllTodoDisplay : Screen("AllTodoDisplay")
    object CategoryTodoDisplay : Screen("CategoryTodoDisplay")
    object DoneDisplay : Screen("DoneDisplay")
    object AddEditTodoScreen : Screen("AddEditTodo")

}