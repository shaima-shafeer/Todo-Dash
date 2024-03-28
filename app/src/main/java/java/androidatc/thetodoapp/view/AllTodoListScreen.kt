package java.androidatc.thetodoapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.data.Todo

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.androidatc.thetodoapp.util.TodoItem
import java.androidatc.thetodoapp.util.TodoListEvent
import java.androidatc.thetodoapp.viewmodel.TodoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavHostController) {

    val context = LocalContext.current
    val viewModel: TodoListViewModel =
        ViewModelProvider(context as ViewModelStoreOwner).get(TodoListViewModel::class.java)
    viewModel.show("NOT DONE")
    val todos by viewModel.todos.observeAsState(initial = emptyList())
    TodoListDisplay(todo = todos, viewModel = viewModel, navController = navController)

}