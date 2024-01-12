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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeConstraintLayout :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Android_ComposeUsesTheme {
                Surface(modifier=Modifier.fillMaxSize()) {
                    ConstraintLayoutEx()
                }
            }
        }
    }
}



@Composable
private fun ConstraintLayoutEx(){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        //step 1 : createRefs()를 이용해서 새 레퍼런스를 가져온다.
        //createRefs()는 여러개으 레퍼런스를 리턴하니 destruction(비구조화)으로 분해한다.
        //Red,Magenta,Yellow,Green 박스가 있다.
        val (redBox,magentaBox,greenBox,yellowBox) = createRefs() //한번에 16개 가져올 수 있다

        //step 2 : constrainAs Modifier를 추가하고 레퍼런스를 전달한다.
        //후행 람다로 top, start, end, bottom 앵커를 지정하고 linkTo로 연결해준다.
        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Red)
            .constrainAs(redBox) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 10.dp)
            })

        //step 3 : 마젠타 박스를 parent의 start,end에 붙여본다
        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Magenta)
            .constrainAs(magentaBox) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Green)
            .constrainAs(greenBox) {
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                top.linkTo(parent.top)
//                bottom.linkTo(parent.bottom)
                //번거로운 작업
                centerTo(parent)
                // = centerHorizontallyTo,centerVerticallyTo
            })

        Box(modifier = Modifier.size(40.dp)
            .background(Color.Yellow)
            .constrainAs(yellowBox){
                top.linkTo(magentaBox.bottom)
                start.linkTo(magentaBox.end)
            })
        }
}


@Composable
@Preview(showBackground = true)
private fun DefaultConstraintLayoutPreview(){
    Android_ComposeUsesTheme {
        ConstraintLayoutEx()
    }
}