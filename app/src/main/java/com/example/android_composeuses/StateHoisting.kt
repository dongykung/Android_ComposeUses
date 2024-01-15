package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class StateHoisting : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                StateHoistingEx()
            }
        }
    }
}

@Composable
private fun StateHoistingEx() {

    //remember는 compose에서 cached되지만 configuration이 될때는 저장되지 않는다
    //configuration(회전,테마를 바꾸고 다시 들어왔을 때 등..)
    //rememberSaveable을 사용하면 됨

    var pyeong by rememberSaveable{ mutableStateOf("23") }
    var squaremeter by rememberSaveable{ mutableStateOf((23*3.306).toString()) }

    //step 1 : remember를 이용해 상태를 만들고  평 값을 입력하면 제곱미터가 나오게한다
    //평을 제곱미터로 바꾸려면 3.306을 곱하면 된다

    //평과 스퀘어미터 정보가 outlinedtextfiled까지와서 전달이 되는 방법은 좋지 못한 방식이다.
    //가능한 한 상태가 전달되는 범위를 좁혀야함.
    //상태가 전달되는 범위를 좁히기 위해서 상태를 윗단으로 끌어올리는 것을 stateHoisting이라고 한다.

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = pyeong, onValueChange = {
            if(it.isBlank()){
                pyeong=""
                squaremeter=""
                return@OutlinedTextField
            }
            val numericValue = it.toFloatOrNull() ?: return@OutlinedTextField
            pyeong=it
            squaremeter = (numericValue * 3.306).toString()
        }, label = { Text(text = "평")})

        OutlinedTextField(value = squaremeter, onValueChange = {

        }, label = { Text(text = "제곱미터")},
            readOnly = true)
    }

    StateHoistingStateless(pyeong = pyeong, squarememter = squaremeter){
        if(it.isBlank()){
            pyeong=""
            squaremeter=""
            return@StateHoistingStateless
        }
        val numericValue = it.toFloatOrNull() ?: return@StateHoistingStateless
        pyeong=it
        squaremeter = (numericValue * 3.306).toString()
    }
}

@Composable
private fun StateHoistingStateless(
    pyeong:String,squarememter:String,onPyengChage : (String)->Unit){
    //어떤 상태가 있는지 모르고 상태에 대해서 관리하지 않음. 상태 책임은 상위에서 책임지게
    Column(modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Bottom) {
        OutlinedTextField(value = pyeong, onValueChange = {
           onPyengChage(it)
        }, label = { Text(text = "평")})
        OutlinedTextField(value = squarememter, onValueChange = {
        }, label = { Text(text = "제곱미터")},
            readOnly = true)
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultStateHoistingPreview() {
    Android_ComposeUsesTheme {
        StateHoistingEx()
    }
}