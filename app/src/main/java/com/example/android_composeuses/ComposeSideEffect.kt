package com.example.android_composeuses

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeSideEffect : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                EffectEx()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun EffectEx(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    var snackbarHostState = remember { SnackbarHostState() }
    //step 1 : LaunchedEffect 를 사용해서 스낵바 생성
    //LaunchedEffect 는 'CoroutineScope'  를 만들기 때문에 스코프를 별도로 만들 피룡가 없다

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar("Hellow Compose")
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("effect", "on_start")
                }

                Lifecycle.Event.ON_STOP -> {
                    Log.d("effect", "on_stop")

                }

                else -> {
                    Log.d("effect", "그 외")

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {

    }

    //step 2 : DisposableEffect를 호출하고 파라미터로 lifecycleOwner를 전달한다.

    //'LifecycleEventObserver' 를 상속받고 두 상태에 대해 로그를 남긴다
    //'Lifecycle.Event.ON_START' , 'Lifecycle.EVENT.ON_STOP'


    //블록 내에서 LifecycleOwner.lifecycle.addObserver를 추가하고
    //onDisposable에서 제거한다

}

@Preview(showBackground = true)
@Composable
private fun DefaultEffectPreview() {
    Android_ComposeUsesTheme {
        EffectEx()
    }
}