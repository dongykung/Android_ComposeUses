package com.example.android_composeuses

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ComposeSnackBar:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Android_ComposeUsesTheme {
                SnackBarEx()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun SnackBarEx(){
    var counter by remember { mutableStateOf(0) }

    //step 3 : coroutineScope를 만들어본다,
    //rememberCoroutineScope를 를 사용한다
    val scope = rememberCoroutineScope() //snack바가 suspend fun 이기 때문
    //suspend는 coroutine 범위안에서만 사용가능

    //step 1 : Scaffold를 만들고 Scaffold에 설정하고 만들기 위해
    // rememberScaffoldState 를 사용해본다.
    val snackbarHostState = remember{SnackbarHostState()}

    Scaffold(snackbarHost = {SnackbarHost(snackbarHostState)}) {
        //step 2 : 더하기 버튼을 만들어준다
        //action 에서 counter를 증가시킨다

       //그냥 coroutineScope를 사용할 수 없음
        // Call to launch should happend inside a LaunchedEffect and not composition
        //scope.launch {  }
        //부수효과에서 다시 공부
        //처음 뜰때만 호출해주고 상태바 바뀌지 않는한 호출 x
        //onclick은 composition이 아니기 때문에 가능했던 것
        LaunchedEffect(snackbarHostState){ //snackbarHostState 가 바뀌기 전까지는 다시호출x
            scope.launch {
                snackbarHostState.showSnackbar(message = "카운터는 ${counter}입니다.",
                    actionLabel = "닫기",
                    duration = SnackbarDuration.Short)
            }
        }



        Button(onClick = {
            counter++
            scope.launch {
                val result = snackbarHostState.showSnackbar(message = "카운터는 ${counter}입니다.",
                    actionLabel = "닫기",
                    duration = SnackbarDuration.Short)
                when(result){
                    SnackbarResult.Dismissed ->{println("dissmiss")}
                    SnackbarResult.ActionPerformed->{println("performed")}
                }
            }
        }) {
            Text(text = "더하기")
        } 

        //step 4 : 버튼의 onclick에서 coroutineScope.launch 를 사용한다.


        //step 5 : SnackBar를 만들기 위해 scaffoldState.snackbarHostState.showSnackBar를 사용한


        //'message'에 카운터를 출력해본다.
        //'actionLabel'를 닫기로 지정한다
        //'duration' 에 SnackbarDuration.Short' 를 사용해본다.
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultSnackBarPreview(){
    Android_ComposeUsesTheme {
        SnackBarEx()
    }
}