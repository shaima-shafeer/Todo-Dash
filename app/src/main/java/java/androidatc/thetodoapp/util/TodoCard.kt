package java.androidatc.thetodoapp.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.androidatc.thetodoapp.data.Todo
import java.androidatc.thetodoapp.ui.theme.textColor
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    navController: NavController,
    color: Color
) {

    var cardcolor = color
    val rcolor = Color(Random.nextLong(0xFFFFFFFF)).copy(alpha = 1F)
    var txtColor = textColor

    if (todo.todoDate < LocalDate.now()) {
        cardcolor = Color.LightGray
        txtColor = Color.DarkGray
    }
    ElevatedCard(

        modifier = Modifier
            .clickable { navController.navigate("${Screen.AddEditTodoScreen.route}/${todo.id}") }
            .padding(30.dp, 15.dp)
            .height(175.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        elevation = CardDefaults.cardElevation(30.dp),
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardcolor)
        )
        {//full row
            Box()
            {
                Column(
                    modifier = Modifier.fillMaxSize()
                )
                {

                    Text(
                        text = todo.title.uppercase(),
                        fontSize = 17.sp,
                        modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp),
                        color = txtColor,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = todo.description ?: "",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .width(200.dp)
                            .padding(20.dp, 10.dp, 0.dp, 5.dp)
                            .alpha(0.7f),
                        softWrap = true,
                        color = txtColor

                    )
                    val days = ChronoUnit.DAYS.between(LocalDate.now(), todo.todoDate)
                    var dayMsg: String? = null
                    val dayMsgModifier: Modifier
                    if (!todo.isDone) {
                        dayMsgModifier = Modifier
                            .padding(20.dp, 0.dp, 0.dp, 0.dp)
                            .alpha(0.5f)
                        dayMsg = if (days < 0) {
                            "You are late by ${Math.abs(days)} days"
                        } else {
                            "No of Days remaining : $days"
                        }
                    } else {
                        dayMsgModifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                    }
                    if (!dayMsg.isNullOrBlank()) {
                        Text(
                            text = dayMsg, modifier = dayMsgModifier,
                            color = txtColor
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(6.dp, 0.dp, 0.dp, 0.dp)
                        .align(Alignment.BottomStart),


                    )

                {
                    IconButton(onClick = {
                        onEvent(TodoListEvent.OnDeleteClick(todo))

                    }

                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(27.dp)
                                .alpha(0.8f)
                                .padding(0.dp, 0.dp, 0.dp, 0.dp),
                            tint = Color.Black

                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = { isChecked ->
                            onEvent(TodoListEvent.OnCheck(todo, isChecked))
                        },
                        modifier = Modifier
                            .size(7.dp),
                        colors=CheckboxDefaults.colors(uncheckedColor = Color.Black)
                    )

                }


            }

            Box(
                Modifier
                    .padding(0.dp, 0.dp, 25.dp, 0.dp)
                    .align(Alignment.CenterEnd)
            )
            {
                CircularProgressIndicator(
                    progress = todo.progress,
                    color = rcolor,
                    strokeWidth = 10.dp,
                    trackColor = Color.White,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .padding(top = 20.dp)

                )
            }


        }
    }
}