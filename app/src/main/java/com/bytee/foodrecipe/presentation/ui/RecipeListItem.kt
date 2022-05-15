package com.bytee.foodrecipe.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import com.bytee.foodrecipe.domain.model.Result
import com.bytee.foodrecipe.R
import com.bytee.foodrecipe.domain.model.FoodRecipe

@Composable
fun RecipeList(result: Result, onItemClick: (Result) -> Unit , playButtonClick : (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClick(result)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,

        ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            val (image, box, play) = createRefs()




            Image(
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                painter = rememberImagePainter(result.thumbnail_url),
                contentDescription = null,
                contentScale = ContentScale.Crop,

                )


            Box(
                modifier = Modifier
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(


                                Color.Transparent,
                                Color.Black,
                            )
                        )
                    )
            ) {
                Text(
                    text = result.name ?: "",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(5.dp),
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    style = MaterialTheme.typography.body1,
                    fontSize = 18.sp
                )


            }

            if (!result.video_url.isNullOrBlank()){
                IconButton(modifier = Modifier
                    .constrainAs(play) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    onClick = {
                        playButtonClick(result.video_url?:"")
                    }) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "null",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(300.dp)
                    )
            }



            }




        }
    }
}