package java.androidatc.thetodoapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.ui.theme.BGColor
import java.androidatc.thetodoapp.ui.theme.CardColors
import java.androidatc.thetodoapp.ui.theme.containercolor
import java.androidatc.thetodoapp.ui.theme.contentColor
import java.androidatc.thetodoapp.ui.theme.textColor
import java.androidatc.thetodoapp.util.Screen
import java.androidatc.thetodoapp.util.TodoItem
import java.androidatc.thetodoapp.viewmodel.TodoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListDisplay(
    todo: List<Todo>,
    navController: NavHostController,
    viewModel: TodoListViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(63, 66, 64),
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor,
                    actionIconContentColor = textColor,
                    scrolledContainerColor = textColor
                ),
                title = {
                    Text(
                        "THINGS TO DO",
                        modifier = Modifier.padding(5.dp, 20.dp),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screen.HomeScreen.route) },
                        modifier = Modifier.padding(10.dp, 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                modifier = Modifier.height(80.dp),
                actions = {
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Filter"
                        )
                    }
                }

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(63, 66, 64))
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .clip(RoundedCornerShape(80.dp, 80.dp, 0.dp, 0.dp))
                    .background(BGColor)
            )

            {
                Spacer(modifier = Modifier.height(30.dp))
                if (todo.isEmpty()) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Nothing To Display!",
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(30.dp)
                            .alpha(0.5f),
                        color = Color.White
                    )

                } else {
                    Box(
                        Modifier
                            .padding(top = 20.dp)
                            .width(300.dp)
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    )
                    {
                        OutlinedTextField(
                            value = searchText, onValueChange = { searchText = it },
                            placeholder = { Text("Search Here.", modifier = Modifier.alpha(0.8f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor
                            )
                        )
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.LightGray
                            )
                        }
                    }
                    val filteredTodos = if (searchText.isNotBlank()) {
                        todo.filter { it.title.contains(searchText, ignoreCase = true) }
                    } else {
                        todo
                    }
                    var counter = 0 // Making this mutable is recomposing UI infinitely
                    LazyColumn(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .padding(0.dp, 40.dp, 0.dp, 0.dp)
                    ) {
                        items(filteredTodos.size) { index ->
                            TodoItem(
                                todo = filteredTodos[index],
                                onEvent = viewModel::onEvent,
                                navController,
                                CardColors[index % 4]
                            )
                        }
                    }
                }
            }
        }

    }
    DropDown(
        navController = navController,
        viewModel = viewModel,
        expanded = expanded,
        onDismissRequest = { expanded = false }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropDown(
    navController: NavHostController,
    viewModel: TodoListViewModel,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {


    if (expanded) {
        val status = listOf("Done", "Not Done")
        val category = listOf("Professional", "Personal", "Health", "Others")
        val orderby = listOf("Date", "Priority")

        Box(
            modifier = Modifier
                .padding(top = 60.dp, end = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = { onDismissRequest() }
            ) {


                DropDownMenuContent(
                    status = status,
                    category = category,
                    orderby = orderby,
                    navController = navController,
                    viewModel = viewModel,
                    onDismissRequest = onDismissRequest
                )


            }
        }
    }
}
@Composable
fun DropDownMenuContent(
    status: List<String>,
    category: List<String>,
    orderby: List<String>,
    navController: NavHostController,
    viewModel: TodoListViewModel,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(250.dp)
            .background(color = textColor)
    ) {
        LazyColumn(Modifier.padding(10.dp)) {
            item {
                Column {
                    DropdownMenuItem(
                        text = { Text("ORDER BY", fontWeight = FontWeight.Bold) },
                        onClick = { }
                    )
                    orderby.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                navController.navigate("${Screen.CategoryTodoDisplay.route}/${option.uppercase()}")
                                onDismissRequest()
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("STATUS", fontWeight = FontWeight.Bold) },
                        onClick = { }
                    )
                    status.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                navController.navigate("${Screen.CategoryTodoDisplay.route}/${option.uppercase()}")
                                onDismissRequest()
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("CATEGORY", fontWeight = FontWeight.Bold) },
                        onClick = { }
                    )
                    category.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                navController.navigate("${Screen.CategoryTodoDisplay.route}/${option.uppercase()}")
                                onDismissRequest()
                            }
                        )
                    }
                }
            }
        }
    }
}
