package java.androidatc.thetodoapp.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import java.androidatc.thetodoapp.ui.theme.BGColor
import java.androidatc.thetodoapp.ui.theme.textColor

import java.androidatc.thetodoapp.util.AddEditTodoEvent
import java.androidatc.thetodoapp.util.Screen
import java.androidatc.thetodoapp.viewmodel.AddEditTodoViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(navController: NavHostController, backStackEntry: NavBackStackEntry) {
    val (displayInvoked, setDisplayInvoked) = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors
                (containerColor = textColor),
                title = {
                    Text("TASK", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current
        val viewModel: AddEditTodoViewModel = hiltViewModel()
        viewModel.todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
        //Log.d("todoId",todoId.toString())

        if (!displayInvoked) {
            viewModel.display()
            setDisplayInvoked(true)
        }

        Log.d("display", "invoked")
        val textfieldModifier: Modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(30.dp, 10.dp)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(textColor)
        )
        {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .clip(RoundedCornerShape(70.dp, 70.dp, 0.dp, 0.dp))
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .background(BGColor)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                DatePickerComponent(viewModel)
                val focusManager = LocalFocusManager.current
                Text(
                    "TITLE",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(30.dp, 15.dp)
                        .alpha(0.7f),
                    color = Color.White

                )
                TextField(
                    value = viewModel.title,
                    onValueChange = {
                        if (it.length <= 30)
                            viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                        else{
                            Toast.makeText(context, "Character Limit Exceeded(30/30)", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = textfieldModifier,
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() })

                )
                Text(
                    "DESCRIPTION",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(30.dp, 15.dp)
                        .alpha(0.7f),
                    color = Color.White
                )
                TextField(
                    value = viewModel.description,
                    onValueChange = {
                        if (it.length <= 50)
                            viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                        else{
                            Toast.makeText(context, "Character Limit Exceeded(50/50)", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = textfieldModifier,
                    minLines = 2,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() })


                )

                Text(
                    "CATEGORY",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(30.dp, 10.dp)
                        .alpha(0.7f),
                    color = Color.White
                )
                CatDisplayCard(
                    Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(20.dp),
                    viewModel
                )


                Progress(viewModel)
                PriorityRadioButton(viewModel)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp), contentAlignment = Alignment.Center
                )
                {
                    Row()
                    {

                        Button(onClick = {
                            if (viewModel.title.isBlank() || viewModel.description.isBlank()) {
                                Toast.makeText(context, "Task Title is Empty.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else {
                                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
                                navController.navigate(Screen.HomeScreen.route)
                            }
                        })
                        {
                            Text("Ok Done")

                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Button(onClick = { viewModel.onEvent(AddEditTodoEvent.OnClearTodoClick) })
                        {
                            Text("Clear")

                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Progress(viewModel: AddEditTodoViewModel) { //add bar display the slot add heading
    Box(Modifier.fillMaxWidth())
    {
        Text(
            text = "PROGRESS", modifier = Modifier
                .padding(30.dp, 10.dp)
                .alpha(0.7f)
                .align(Alignment.CenterStart),
            color = Color.White, fontWeight = FontWeight.Bold
        )
        Text(
            text = "${(viewModel.progress * 100).roundToInt()} %", modifier = Modifier
                .padding(30.dp, 10.dp)
                .align(Alignment.CenterEnd)
                .alpha(0.7f),
            color = Color.White, fontWeight = FontWeight.Bold
        )
    }

    Column {

        Slider(
            value = viewModel.progress,
            onValueChange = { viewModel.progress = it },
            modifier = Modifier.padding(40.dp, 10.dp)
        )

    }
}

@Composable
fun ChooseCategory(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
        ),
        modifier = modifier
            .width(120.dp)
            .height(70.dp)
            .padding(0.dp),
        shape = RectangleShape
    ) {
        Text(text = category)
    }
}

@Composable
fun CatDisplayCard(cardmodifier: Modifier, viewModel: AddEditTodoViewModel) {

    Column {
        Row {
            ChooseCategory(
                "PROFESSIONAL",
                isSelected = viewModel.category == "PROFESSIONAL",
                onClick = { viewModel.category = "PROFESSIONAL" },
                modifier = cardmodifier
            )
            ChooseCategory(
                "PERSONAL",
                isSelected = viewModel.category == "PERSONAL",
                onClick = { viewModel.category = "PERSONAL" },
                modifier = cardmodifier
            )
        }
        Row {
            ChooseCategory(
                "HEALTH",
                isSelected = viewModel.category == "HEALTH",
                onClick = { viewModel.category = "HEALTH" },
                modifier = cardmodifier
            )
            ChooseCategory(
                "OTHERS",
                isSelected = viewModel.category == "OTHERS",
                onClick = { viewModel.category = "OTHERS" },
                modifier = cardmodifier
            )
        }
    }
}


@Composable
fun PriorityRadioButton(
    viewModel: AddEditTodoViewModel
) {
    Text(
        text = "PRIORITY", modifier = Modifier
            .padding(30.dp, 10.dp)
            .alpha(0.7f),
        color = Color.White, fontWeight = FontWeight.Bold
    )
    val radioOptions = listOf("HIGH", "MEDIUM", "LOW")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[viewModel.priority]) }
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        radioOptions.forEach { text ->
            Box(modifier = Modifier.width(125.dp)) {
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                )
                {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .align(Alignment.CenterVertically)
                            .alpha(0.7f),
                        color = Color.White
                    )
                }
            }

        }
    }
    when (selectedOption) {
        "HIGH" -> viewModel.priority = 0
        "MEDIUM" -> viewModel.priority = 1
        "LOW" -> viewModel.priority = 2
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(viewModel: AddEditTodoViewModel) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.date.toEpochMilli(),
        yearRange = 2024..2100
    )
    Log.d("in Screen day", datePickerState.toString())
    var showDatePicker by remember { mutableStateOf(false) }
    Text(
        text = "DEADLINE", modifier = Modifier
            .padding(30.dp, 10.dp)
            .alpha(0.7f),
        color = Color.White, fontWeight = FontWeight.Bold
    )

    Row(
        modifier = Modifier
            .padding(30.dp,16.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, textColor)
                .padding(30.dp,10.dp)
        )
        {

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                    ) {
                        append(viewModel.date.toString())
                    }
                },
                modifier = Modifier
                    .padding(10.dp, 16.dp)
                    .alpha(0.7f)
                    .align(Alignment.CenterStart),
                onClick = {
                    showDatePicker = true
                }
            )
            IconButton(
                onClick = {
                    showDatePicker = true
                },
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar Icon",
                    tint = Color.LightGray
                )
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.date =
                                Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            showDatePicker = false
                        }
                    ) { Text("OK") }
                },
                dismissButton =
                {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) { Text("Cancel") }
                }
            )
            {
                DatePicker(state = datePickerState)
            }
        }
    }
}

fun LocalDate.toEpochMilli(): Long {
    val startOfDay = this.atStartOfDay(ZoneId.systemDefault())
    return startOfDay.toInstant().toEpochMilli()
}