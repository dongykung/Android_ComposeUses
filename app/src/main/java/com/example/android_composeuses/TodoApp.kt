package com.example.android_composeuses

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class TodoApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(Modifier.fillMaxSize()) {
                    TopLevel()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun TopLevel() {
    val (text, setText) = remember { mutableStateOf("") }
    val toDoList = remember { mutableStateListOf<ToDoData>() }
    val onSubmit: (String) -> Unit = {
        val key = (toDoList.lastOrNull()?.key ?: 0) + 1
        toDoList.add(ToDoData(key, it))
        setText("")
    }
    val onToggle: (Int, Boolean) -> Unit = { key, checked ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList[i] = toDoList[i].copy(done = checked)
    }
    val onDelete: (Int) -> Unit = { key ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList.removeAt(i)
    }
    val onEditing: (Int, String) -> Unit = { key, text ->
        val i = toDoList.indexOfFirst { it.key == key }
        toDoList[i] = toDoList[i].copy(text = text)
    }
    Scaffold {
        Column {
            ToDoInput(
                text = text,
                onTexetChanged = setText,
                onSubmit = onSubmit,
            )

            LazyColumn {
                //key를 설정해서 조금 더 효율적으로 렌더링 가능
                items(toDoList,key = {it.key}) {
                    ToDo(
                        todoData = it,
                        onToggle = onToggle,
                        onDelete = onDelete,
                        onEdit = onEditing
                    )
                }
            }

        }
    }
}


@Composable
private fun ToDoInput(
    text: String,
    onTexetChanged: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text, onValueChange = onTexetChanged,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = { onSubmit(text) }) {
            Text(text = "입력")
        }

    }
}

@Composable
private fun ToDo(
    todoData: ToDoData,
    onEdit: (Key: Int, text: String) -> Unit = { _, _ -> },
    onToggle: (Key: Int, checkd: Boolean) -> Unit = { _, _ -> },
    onDelete: (Key: Int) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {

        Crossfade(targetState = isEditing, label = "") {
            when (it) {
                false -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = todoData.text, Modifier.weight(1f))
                        Text(text = "완료")
                        Checkbox(checked = todoData.done, onCheckedChange = {
                            onToggle(todoData.key, it)
                        })
                        Button(onClick = {
                            isEditing = true
                        }) {
                            Text(text = "수정")
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(onClick = { onDelete(todoData.key) }) {
                            Text(text = "삭제")
                        }
                    }
                }

                true -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        var newText by remember { mutableStateOf(todoData.text) }
                        OutlinedTextField(
                            value = newText, onValueChange = {newText=it},
                            Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(onClick = {
                            onEdit(todoData.key,newText)
                            isEditing = false
                        }) {
                            Text(text = "완료")
                        }

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultInputPreview() {
    Android_ComposeUsesTheme {
        ToDoInput(text = "test", onTexetChanged = {}, onSubmit = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ToDoPreview() {
    Android_ComposeUsesTheme {
        ToDo(ToDoData(1, "test", false))
    }
}

data class ToDoData(
    val key: Int,
    val text: String,
    val done: Boolean = false
)