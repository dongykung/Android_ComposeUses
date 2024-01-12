package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeConstraintSet : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ConstraintSetEx()
                }
            }
        }
    }
}

@Composable
private fun ConstraintSetEx(){
    val constraintSet : ConstraintSet = ConstraintSet{
        //step 1 : createRefFor로 레드,마젠타,그린,옐로우 박스를 위한 레퍼런스를 생성
        //파라미터 id로 레퍼런스의 이름을 적어준
        val redBox = createRefFor("redBox")
        val magentaBox = createRefFor("magentaBox")
        val greenBox = createRefFor("greenBox")
        val yellowBox = createRefFor("yellowBox")


        //step 2 : 'constrain'을 열고 만들었던 레퍼런스를 인자로 넣어준다.
        //레드, 마젠타, 그린, 옐로 박스 레퍼런스에 대 'constrain' 설정한다.
        //후행 람다의 내용은 기존에 'constrainAs' 를 참조
        constrain(redBox){
            bottom.linkTo(parent.bottom, margin = 8.dp)
            end.linkTo(parent.end, margin = 10.dp)
        }
        constrain(magentaBox){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(greenBox){
            centerTo(parent)
        }
        constrain(yellowBox){
            top.linkTo(magentaBox.bottom)
            start.linkTo(magentaBox.end)
        }
    }

    ConstraintLayout(constraintSet = constraintSet,modifier=Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Red)
            .layoutId("redBox")
            )

        //step 3 : 마젠타 박스를 parent의 start,end에 붙여본다
        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Magenta)
            .layoutId("magentaBox"))

        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Green)
            .layoutId("greenBox"))

        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Yellow)
            .layoutId("yellowBox"))
    }

}




@Composable
@Preview(showBackground = true)
private fun DefaultConstraintSetPreview(){
    Android_ComposeUsesTheme {
        ConstraintSetEx()
    }
}