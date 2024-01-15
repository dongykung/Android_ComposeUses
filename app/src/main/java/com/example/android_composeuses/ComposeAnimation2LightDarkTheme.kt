package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeAnimation2LightDarkTheme : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AnimationEx2()
                }
            }
        }
    }
}

@Composable
private fun AnimationEx2() {
    var isDarkMode by remember { mutableStateOf(false) }

    //step 1 : updateTransition을 수행하고 tragetState를 'isDarkMode'로 설정한다.
    //transition을 리턴으로 받는다
    val transition = updateTransition(targetState = isDarkMode, label = "다크 모드 트랜지션")
    //step 2 : transition에 대해 'animateColor'를 호출해 'backgroundColor'를 받는다/
    //배경 색상을 만든다. False일 때 하얀배경, true일 때 검은배경
    val backgroundColor by transition.animateColor(label = "다크 모드 배경 색상 애니") { state ->
        when (state) {
            false -> Color.White
            true -> Color.Black
        }
    }
    //by를 쓰는 이유는 반환값으로 state<color>가 오기 때문
    //step 3 : 글자 색상을 만든다.
    val color by transition.animateColor(label = "다크모드 글자 색상") {
        when (it) {
            false -> Color.Black
            true -> Color.White
        }
    }
    //step 4 : 'animateFloat'를 호출해 알파값을 만든다.
    val alpha by transition.animateFloat(label = "다크 모드 알파") {
        when (it) {
            false -> 0.7f
            true -> 1.0f
        }
    }

    //step 5 : 컬럼에 배경과 색상을 지정한다

    Column(modifier = Modifier
        .background(backgroundColor)
        .alpha(alpha)) {
        //Stpe 6 : 라디오 버튼에 글자 색을 지정
        RadioButtonWithText(text = "일반모드", color = color ,seleted = !isDarkMode) {
            isDarkMode = false
        }
        RadioButtonWithText(text = "다크모드",color=color ,seleted = isDarkMode) {
            isDarkMode = true
        }
        //step 7 : Crossfade를 이용해 isDarkMode가 참일 시
        //Row항목을 표현하고 거짓일 시 Column으로 표현해본다

//        Row {
//            Box(
//                modifier = Modifier
//                    .background(Color.Red)
//                    .size(20.dp)
//            ) {
//                Text(text = "1")
//            }
//            Box(
//                modifier = Modifier
//                    .background(Color.Magenta)
//                    .size(20.dp)
//            ) {
//                Text(text = "2")
//            }
//            Box(
//                modifier = Modifier
//                    .background(Color.Blue)
//                    .size(20.dp)
//            ) {
//                Text(text = "3")
//            }
//        }
        //if문을 사용할 수 잇겠지만 Crossfade 를 사용한다면 애니메이션 효과를 줄 수 있음.
        Crossfade(targetState = isDarkMode, label = "") {
            when(it){
                false->{
                    Column {
                        Box(
                            modifier = Modifier
                                .background(Color.Red)
                                .size(20.dp)
                        ) {
                            Text(text = "1")
                        }
                        Box(
                            modifier = Modifier
                                .background(Color.Magenta)
                                .size(20.dp)
                        ) {
                            Text(text = "2")
                        }
                        Box(
                            modifier = Modifier
                                .background(Color.Blue)
                                .size(20.dp)
                        ) {
                            Text(text = "3")
                        }
                    }
                }
                    true->{
                        Row {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red)
                                    .size(20.dp)
                            ) {
                                Text(text = "1")
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.Magenta)
                                    .size(20.dp)
                            ) {
                                Text(text = "2")
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .size(20.dp)
                            ) {
                                Text(text = "3")
                            }
                        }
                    }
            }
        }
    }


}


@Composable
@Preview(showBackground = true)
private fun DefaultAnimation2Preview() {
    Android_ComposeUsesTheme {
        AnimationEx2()
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultRadioButtonWithTextPreview() {
    Android_ComposeUsesTheme {
        RadioButtonWithText(text = "라디오 버튼",
            color = Color.Red,
            seleted = true,
            onClick = {})
    }
}


@Composable
private fun RadioButtonWithText(
    text: String,
    color: Color = Color.Black,
    seleted: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.selectable(selected = seleted, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //radioButton은 이벤트를 다 가로채가기 때문에 row,radiobutton둘다 onclick해줘야
        RadioButton(selected = seleted, onClick = onClick)
        Text(text = text, color = color)
    }
}
