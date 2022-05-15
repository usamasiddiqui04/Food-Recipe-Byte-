package com.bytee.foodrecipe.presentation.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.bytee.foodrecipe.R
import com.bytee.foodrecipe.domain.model.Result
import com.bytee.foodrecipe.presentation.ui.RecipeList
import com.bytee.foodrecipe.presentation.ui.activity.ui.theme.FoodRecipeTheme
import com.bytee.foodrecipe.presentation.viewmodel.FoodRecipeViewModel
import com.bytee.foodrecipe.presentation.viewmodel.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeDetailActivity : ComponentActivity() {


    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()

    lateinit var name: String
    lateinit var image: String
    lateinit var des: String
    private var id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = intent.extras?.getString("recipeName").toString()
        image = intent.extras?.getString("recipeImage").toString()
        des = intent.extras?.getString("recipeDec").toString()
        id = intent.extras?.getInt("recipeId")
        recipeDetailsViewModel.getList(id!!)


        setContent {
            FoodRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeDetailUI(
                        name = name,
                        des = des,
                        image = image,
                        viewModel = recipeDetailsViewModel,
                        id = id!!,
                        onPlayVideo = {

                        }, onItemClick = {

                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipeDetailUI(
    name: String,
    image: String,
    des: String,
    id: Int,
    viewModel: RecipeDetailsViewModel,
    onItemClick: (Result) -> Unit,
    onPlayVideo: (String) -> Unit
) {



    val apiState by viewModel.recipeApiState.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (card, loader, listView) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(parent.top)
                }

                .fillMaxWidth()
                .wrapContentWidth()
                .padding(5.dp)
                .clickable {
                },
            shape = RoundedCornerShape(10.dp),
            elevation = 10.dp,

            ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                val (imageView, textViewName, textViewDes) = createRefs()
                Image(
                    modifier = Modifier
                        .constrainAs(imageView) {
                            top.linkTo(parent.top)
                        }
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    painter = rememberImagePainter(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,

                    )

                Text(
                    modifier = Modifier
                        .constrainAs(textViewName) {
                            top.linkTo(imageView.bottom)
                        }
                        .padding(10.dp),
                    text = name,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    style = MaterialTheme.typography.h2,
                    fontSize = 18.sp
                )

                if (des.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .constrainAs(textViewDes) {
                                top.linkTo(textViewName.bottom)
                            }
                            .padding(10.dp),
                        text = des,
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        style = MaterialTheme.typography.body1,
                        fontSize = 16.sp
                    )
                }

            }
        }

        if (apiState.loading) {
            Box(modifier = Modifier.constrainAs(loader){
                top.linkTo(card.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {

                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.Black
                )

            }
        } else if (!apiState.loading) {

            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(listView) {
                        top.linkTo(card.bottom, 20.dp)
                    }
                    .padding(bottom = 100.dp),
                contentPadding = PaddingValues(10.dp)
            ) {

                items(apiState.data!!.results!!.size) { item ->
                    RecipeList(
                        result = apiState.data!!.results!![item],
                        onItemClick = onItemClick,
                        onPlayVideo
                    )
                }

            }
        }


    }

}

