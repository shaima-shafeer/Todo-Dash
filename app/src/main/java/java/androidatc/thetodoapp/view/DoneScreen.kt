package java.androidatc.thetodoapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.viewmodel.TodoListViewModel

@Composable
fun DoneScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: TodoListViewModel = hiltViewModel()
    viewModel.show("DONE")
    val todos by viewModel.todos.observeAsState(initial = emptyList())
    TodoListDisplay(todo = todos, navController = navController, viewModel = viewModel)


}