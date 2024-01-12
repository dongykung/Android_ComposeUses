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
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme

class ConstrraintChainBarrier:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Chain_BarrierEx()
                }
            }
        }
    }
}


@Composable
private fun Chain_BarrierEx(){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (redBox,yellowBox,magentaBox,TextTest) = createRefs()
        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Red)
            .constrainAs(redBox) {
                top.linkTo(parent.top, margin = 18.dp)
            })

        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Yellow)
            .constrainAs(yellowBox) {
                top.linkTo(parent.top, margin = 24.dp)
            })

        Box(modifier = Modifier
            .size(40.dp)
            .background(Color.Magenta)
            .constrainAs(magentaBox) {
                top.linkTo(parent.top, margin = 32.dp)
            })


        //step 1 : createVerticalChain,createHorizontalChain를 이용해
        //세 박스의 레퍼런스를 연결해본다.
        //createVerticalChain(redBox,yellowBox,magentaBox)
        //createHorizontalChain(redBox,yellowBox,magentaBox)
        //간격이 일치하게됨(균형이 이루어짐)


        //step 2 : createHorizontalChain 을 사용하고 chainStyle 키워드
        //파라미터를 추가해본다.
        //ChainStyle.Packed, ChainStyle.Spread, ChainStyle.SpreadInside
        //Spread = 균등하게 배치  Packed = 뭉쳐서 배치  SpreadInside = 레드,마젠타사이에만 펼치겟다
        //createHorizontalChain(redBox,yellowBox,magentaBox, chainStyle = ChainStyle.SpreadInside)

        //step 3 : 세 박스의 top을 parent.top에 연결하고 각각 다른 마진을 준다.
        createVerticalChain(redBox,yellowBox,magentaBox, chainStyle = ChainStyle.Packed)


        //step 4 : 'createBottomBarrier' 로 배리어를 만든다.
        //이는 모든 박스들의 하단을 포함하 배릴어입니다.
        var barrier = createBottomBarrier(redBox,yellowBox,magentaBox)

        //step 5 : 'Text'하나 만들고 top을 박스 배리어로 지정합니다.
        androidx.compose.material3.Text(text = "배리어 테스트입니다.배리어 테스트입니다.배리어 테스트입니다.배리어 테스트입니다.배리어 테스트입니다.배리어 테스트입니다.",
            modifier = Modifier.constrainAs(TextTest){
                top.linkTo(barrier)
            })

        //step 6 : 체이닝 방향이나, 배리어 방향을 바꾸어 다양하게 테스트해본다


    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultChain_BarrierPreview(){
    Chain_BarrierEx()
}