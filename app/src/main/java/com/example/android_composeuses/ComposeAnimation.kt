package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeAnimation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComposeAnimationEx()
                }
            }
        }
    }
}


@Composable
private fun ComposeAnimationEx() {
    var helloWorldVisible by remember { mutableStateOf(true) }
    var isRed by remember { mutableStateOf(false) }
   //  val backgroundColor = Color.LightGray

    //step 3 : backgroundColor를 animateColorAsState 로 변경해본다.
    //'targetValue' 는 'isRed' 에 따라 Color를 설정한다
    val backgroundColor by animateColorAsState(targetValue = if(isRed)Color.Red else Color.White, label = "")
    val alpha by animateFloatAsState(targetValue = if(isRed) 1.0f else 0.5f, label = "")
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(backgroundColor)
            .alpha(alpha)
    ) {

        //step 1 : Text를 AnimatedVisibility로 감싸고 visible을 hellowWorldVisible로 설정
//        AnimatedVisibility(visible = helloWorldVisible) {
//            Text(text = "HellowWorld")
//        }
        // Text(text = "Hello World!")
        //step 2 : enter 파라미터 이용해보기
        //예) expandHorizontally()
        //scaleIn(), slideHorizontally(),fadeIn()
        AnimatedVisibility(visible = helloWorldVisible,
            enter = fadeIn()+ expandHorizontally(),
            exit = fadeOut()+ shrinkVertically()
        ) {
            Text(text = "HellowWorld!")
        }
        Row(modifier = Modifier.selectable(
            selected = helloWorldVisible,
            onClick = { helloWorldVisible = true }
        ), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = helloWorldVisible, onClick = { helloWorldVisible = true })
            Text(text = "Hellow World 보이기")
        }

        Row(
            modifier = Modifier.selectable(
                selected = !helloWorldVisible,
                onClick = { helloWorldVisible = false }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = !helloWorldVisible, onClick = { helloWorldVisible = false })
            Text(text = "Hellow World 감추기")
        }

        Text(text = "배경 색을 바꿔 봅시다.")
        Row(
            modifier = Modifier.selectable(selected = !isRed, onClick = { isRed = false }),
            verticalAlignment = Alignment.CenterVertically
        ) {
                RadioButton(selected = !isRed, onClick = {isRed=false})
            Text(text = "흰색")
        }

        Row(
            modifier = Modifier.selectable(selected = isRed, onClick = { isRed = true }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = isRed, onClick = {isRed=true})
            Text(text = "빨간색")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultAnimationPreview() {
    Android_ComposeUsesTheme {
        ComposeAnimationEx()
    }
}