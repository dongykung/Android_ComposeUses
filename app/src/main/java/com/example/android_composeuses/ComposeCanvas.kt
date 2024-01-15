package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ComposeCanvas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Android_ComposeUsesTheme {

            }
        }
    }
}

@Composable
private fun CanvasEx(){
    Canvas(modifier = Modifier.size(20.dp)){
        //step 1 :  drawLine 사용,파라미터로 색상,시작(offset) 끝(offset)을 지정해본다.
        drawLine(Color.Red, Offset(10f,10f),Offset(20f,15f))

        //step 2 : drawCircle 사용  색상,반지름,원의 중앙 위치
        drawCircle(Color.Yellow,10f,Offset(15f,30f))

        drawRect(Color.Magenta,Offset(30f,30f), Size(10f,10f))

        Icons.Filled.Send

        drawLine(Color.Green,Offset(2.01f,21.0f),Offset(23.0f,12.0f ))
        drawLine(Color.Green,Offset(23.0f,12.0f),Offset(2.01f,3.0f ))
        drawLine(Color.Green,Offset(2.01f,3.0f),Offset(2.0f,10.0f ))
        drawLine(Color.Green,Offset(2.0f,10.0f),Offset(17.0f,12.0f ))
        drawLine(Color.Green,Offset(17f,12.0f),Offset(2.0f,14.0f ))
        drawLine(Color.Green,Offset(2.0f,14.0f),Offset(2.01f,21.0f ))





    }
}


@Composable
@Preview(showBackground = true)
private fun DEfaultCanvasPreview(){
    Android_ComposeUsesTheme {
        CanvasEx()
    }
}
