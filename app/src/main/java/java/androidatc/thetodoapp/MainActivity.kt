package java.androidatc.thetodoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.androidatc.thetodoapp.ui.theme.TheTodoAppTheme
import java.androidatc.thetodoapp.util.Screen
import java.androidatc.thetodoapp.view.TodoListScreen
import dagger.hilt.android.AndroidEntryPoint
import java.androidatc.thetodoapp.view.AddEditTodoScreen
import java.androidatc.thetodoapp.view.ClassifyTodoScreen
import java.androidatc.thetodoapp.view.DoneScreen
import java.androidatc.thetodoapp.view.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                composable(Screen.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
                composable(Screen.AllTodoDisplay.route) {
                    TodoListScreen(navController = navController)
                }
                composable(
                    route = "${Screen.AddEditTodoScreen.route}/{todoId}",
                    arguments = listOf(
                        navArgument("todoId") {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) { backStackEntry ->
                    AddEditTodoScreen(
                        backStackEntry = backStackEntry,
                        navController = navController
                    )
                }
                composable(
                    route = "${Screen.CategoryTodoDisplay.route}/{category}",
                    arguments = listOf(
                        navArgument("category") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) { backStackEntry ->
                    ClassifyTodoScreen(
                        backStackEntry = backStackEntry,
                        navController = navController
                    )
                }
                composable(Screen.DoneDisplay.route) {
                    DoneScreen(navController = navController)
                }
            }
            printf()
        }

    }
}
    @Composable
    fun printf() {
        Log.d("Done", "So Far Sooo Good")
    }
