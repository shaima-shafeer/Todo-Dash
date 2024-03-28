package java.androidatc.thetodoapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.viewmodel.TodoListViewModel

@Composable
fun ClassifyTodoScreen(navController: NavHostController, backStackEntry: NavBackStackEntry) {
    val context = LocalContext.current
    val viewModel: TodoListViewModel = ViewModelProvider(
        context as ViewModelStoreOwner
    ).get(TodoListViewModel::class.java)
    val category = backStackEntry.arguments?.getString("category") ?: ""
    viewModel.show(category)
    val todos by viewModel.todos.observeAsState(initial = emptyList())
    TodoListDisplay(todo = todos, navController = navController, viewModel = viewModel)

}

