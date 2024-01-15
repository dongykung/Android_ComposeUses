package com.example.android_composeuses

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ComposeBottomAppBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                BottomAppBarEx()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun BottomAppBarEx() {
    var snackbarHostState = remember { SnackbarHostState() }
    val coroutinScope = rememberCoroutineScope()
    var count by remember { mutableStateOf(0) }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, bottomBar = {
        BottomAppBar {
            Text(text = "헬로")
            Button(onClick = {
                coroutinScope.launch {
                    snackbarHostState.showSnackbar(
                        "안녕하세요",
                        actionLabel = "확인",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Text(text = "인사하기")
            }
            Button(onClick = {
                count++
                coroutinScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "카운터는 ${count}입니다",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Text(text = "더하기")
            }
            Button(onClick = {
                count--
                coroutinScope.launch {
                    snackbarHostState.showSnackbar(
                        "카운터는 ${count}입니다",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Text(text = "빼기")
            }
        }
    })
    {
        //LaunchedEffect = Composable이 호출될 때 생성되는 API 한번 호출됨
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "카운터는 ${count}입니다",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun DefaultBottomAppBarPreview() {
    Android_ComposeUsesTheme {
        BottomAppBarEx()
    }
}