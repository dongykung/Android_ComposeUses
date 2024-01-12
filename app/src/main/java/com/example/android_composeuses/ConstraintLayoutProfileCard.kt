package com.example.android_composeuses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.android_composeuses.ComposeConstraintLayoutProfileCard.Companion.cardData
import com.example.android_composeuses.ui.theme.Android_ComposeUsesTheme


class ComposeConstraintLayoutProfileCard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_ComposeUsesTheme {
                Surface(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayoutProfileCardEx()
                }
            }
        }
    }
    companion object{
        var cardData = CardData(
            imageUri = "https://firebasestorage.googleapis.com/v0/b/chattingapp-c1231.appspot.com/o/8JJdr8wpIDeqVmFFkW67gSF2dC02%2F8JJdr8wpIDeqVmFFkW67gSF2dC02profile.jpg?alt=media&token=77679bf2-f641-4edd-9d86-398072125cabhttps://firebasestorage.googleapis.com/v0/b/chattingapp-c1231.appspot.com/o/8JJdr8wpIDeqVmFFkW67gSF2dC02%2F8JJdr8wpIDeqVmFFkW67gSF2dC02profile.jpg?alt=media&token=77679bf2-f641-4edd-9d86-398072125cab",
            imageDescription = "snow",
            author = "김동경",
            description = "안녕하세요 안드로이드 개발을 하고 있는 김동경이라고 합니다"
        )
    }
}

@Composable
private fun ConstraintLayoutProfileCardEx(){
    val placeholderColor = Color(0x33000000)
    Card(modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(25.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            val (profileImage,name,description) = createRefs()

            AsyncImage(model = cardData.imageUri, contentDescription = cardData.imageDescription,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(color = placeholderColor),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .constrainAs(profileImage) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start, margin = 8.dp)
                    })

            Text(text = cardData.author,
                modifier = Modifier.constrainAs(name){
                    linkTo(profileImage.end,parent.end,
                        startMargin = 8.dp, endMargin = 8.dp)
                    width = Dimension.fillToConstraints
                })

            
            Text(text = cardData.description,
                modifier = Modifier.constrainAs(description){
                    start.linkTo(profileImage.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                })

           val chain =  createVerticalChain(name,description, chainStyle = ChainStyle.Packed)
            constrain(chain){
                top.linkTo(parent.top, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }
            //Text의 modifier.constrainAs에서 top,bottom설정을 해주면 되지 않나요?
            //createVerticalChain을 한 순간 vertical에 관련된 link가 다 꺼져버러기 때문에 chain에서 생성한다
        }
    }
}



@Composable
@Preview(showBackground = true)
private fun DefaultConstraintProfileView(){
    Android_ComposeUsesTheme {
        ConstraintLayoutProfileCardEx()
    }
}

data class CardData(
    val imageUri : String,
    val imageDescription : String,
    val author : String,
    val description : String
)