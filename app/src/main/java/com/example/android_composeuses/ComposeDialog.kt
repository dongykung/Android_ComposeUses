package com.example.android_composeuses

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeDialog : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DialogEx()
                }
            }
        }
    }
}


@Composable
private fun DialogEx() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }


    Column {
        Button(onClick = { openDialog = true }) {
            Text(text = "다이얼로그 열기")
        }
        Text(text = "카운터 = $counter")
    }

    if (openDialog) {
        AlertDialog(onDismissRequest = {
            //step 1 : openDialog를 이용 다이얼 로그를 끌 수 있게 합니다
            //다이얼로그가 열렷을 때 다이얼로그 밖을 클릭했을 때
            openDialog = false
        },
            confirmButton = {
                //step 2 : 더하기 버튼을 만들고 counter를 증가시킨다
                Button(onClick = {
                    counter++
                    openDialog = false
                }) {
                    Text(text = "더하기")
                }
            },
            dismissButton = {
                //step 3 : 취소 버튼을 만들고 다이얼로그를 종료시킨다
                Button(onClick = { openDialog = false }) {
                    Text(text = "취소")
                }
            },
            title = {
                //step 4 : 타이틀을 만든다
                Text(text = "더하기")    
            },
            text = {
                //Step 5 : 다이얼로그 설명문구를 만든다
                Text(text = "더하기 버튼을 누르면 카운터를 증가시킵니다")
            })
    }
}


@Composable
@Preview(showBackground = true)
private fun DefaultDialogPreview() {
    Android_ComposeUsesTheme {
        DialogEx()
    }
}