package java.androidatc.thetodoapp.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.ui.theme.BGColor
import java.androidatc.thetodoapp.ui.theme.CardColors
import java.androidatc.thetodoapp.ui.theme.containercolor
import java.androidatc.thetodoapp.ui.theme.contentColor
import java.androidatc.thetodoapp.ui.theme.textColor
import java.androidatc.thetodoapp.util.Screen
import java.androidatc.thetodoapp.util.TodoItem
import java.androidatc.thetodoapp.viewmodel.HomeViewModel
import java.androidatc.thetodoapp.viewmodel.TodoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()

    val alltodos by viewModel.alltodo.observeAsState(initial = emptyList())
    val todaytodos by viewModel.todaytodo.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TASK-MASTER",
                        fontWeight = FontWeight.Bold
                    )
                }, scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                    rememberTopAppBarState()
                ),
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(0.dp, 0.dp, 7.dp, 7.dp)
                    )
            )

        },

        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
                    ), containerColor = MaterialTheme.colorScheme.primary
            ) {
                IconButton(
                    onClick = { navController.navigate(Screen.DoneDisplay.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Home")
                }

                IconButton(
                    onClick = { navController.navigate("${Screen.AddEditTodoScreen.route}/-1") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

            }
        }
    )
    { paddingValues ->

        Column(
            modifier = Modifier
                .background(containercolor)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
        {
            IntroCard("Buddy!")
            PendingCard({ navController.navigate(Screen.AllTodoDisplay.route) }, alltodos.size)
            CategoryDisplay(navController)




            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(70.dp, 70.dp, 0.dp, 0.dp))
                    .background(BGColor)
            )
            {
                Column()
                {
                    Text(
                        text = "TODAYS TASK",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp, 30.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                    Tasks(todaytodos, context, navController)

                }

            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingCard(onclick: () -> Unit, remainingTask: Int) {

    ElevatedCard(
        colors = CardDefaults.cardColors(contentColor), modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(20.dp, 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = onclick
    ) {
        Text(
            text = "No of Pending Task : ${remainingTask}",//add no of task
            modifier = Modifier.padding(15.dp),
            fontSize = 20.sp,
            lineHeight = 40.sp, color = textColor
        )
    }
}

@Composable
fun IntroCard(name: String) {
    Card(
        modifier = Modifier
            .padding(10.dp, 20.dp)
            .fillMaxWidth(), colors = CardDefaults.cardColors(contentColor)
    )
    {
        Column(
            modifier = Modifier
                .padding(20.dp, 20.dp)
                .background(contentColor)
        )
        {
            Text(text = "Welcome $name!", fontSize = 30.sp, color = textColor)
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = "Ready for Todays Tasks? ", fontSize = 20.sp, color = textColor)
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = "Hustle!", fontSize = 15.sp, color = textColor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: String, navController: NavHostController)//make onclick
{
    var hoverColor = remember { contentColor }
    ElevatedCard(
        colors = CardDefaults.cardColors(hoverColor),
        modifier = Modifier
            .padding(15.dp, 25.dp)
            .height(100.dp)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = {
            hoverColor= Color.LightGray
            navController.navigate("${Screen.CategoryTodoDisplay.route}/$category")
            Log.d("Category", category)
        }
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(15.dp, 15.dp),
            fontSize = 16.sp,
            color = textColor
        )
    }
}

@Composable
fun CategoryDisplay(navController: NavHostController) {

    val cat = listOf("PROFESSIONAL", "PERSONAL", "HEALTH", "OTHERS")
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        items(cat) { category ->
            CategoryCard(category = category, navController)
        }
    }

}

@Composable
fun Tasks(todaytodos: List<Todo>, context: Context, navController: NavHostController) {
    val viewModel: TodoListViewModel =
        ViewModelProvider(context as ViewModelStoreOwner).get(TodoListViewModel::class.java)

    if (todaytodos.isEmpty()) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        )
        {
            Box(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            {
                Text(
                    text = "no tasks for today:)", modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.5f),
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, color = textColor
                )
            }
        }
    } else {
        Box(modifier = Modifier.height(500.dp)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(70.dp, 70.dp, 0.dp, 0.dp))
                    .background(color = Color.Transparent)
            ) {
                items(todaytodos) { todo ->
                    TodoItem(
                        todo = todo,
                        onEvent = viewModel::onEvent,
                        navController = navController,
                        color = Color(153, 75, 201)
                    )

                }
            }
        }
    }
}
