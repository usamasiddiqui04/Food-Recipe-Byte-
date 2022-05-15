package com.bytee.foodrecipe.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bytee.foodrecipe.R
import com.bytee.foodrecipe.data.utils.UiState
import com.bytee.foodrecipe.domain.model.FoodRecipeRequest
import com.bytee.foodrecipe.domain.model.Result
import com.bytee.foodrecipe.presentation.ui.RecipeList
import com.bytee.foodrecipe.presentation.viewmodel.FoodRecipeViewModel
import com.bytee.foodrecipe.ui.theme.FoodRecipeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: FoodRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.getList(FoodRecipeRequest(size = 20 , from = 0 ,q = ""))
        setContent {
            FoodRecipeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeUiScreen(viewModel = viewModel, onItemClick = {
                        val intent = Intent(this, RecipeDetailActivity::class.java)
                        intent.putExtra("recipeName", it.name)
                        intent.putExtra("recipeImage", it.thumbnail_url)
                        intent.putExtra("recipeDec", it.description)
                        intent.putExtra("recipeId", it.id)
                        startActivity(intent)
                    }, onPlayVideo = {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        startActivity(browserIntent)
                    })
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodRecipeTheme {
        Greeting("Android")
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
fun RecipeUiScreen(
    viewModel: FoodRecipeViewModel,
    onItemClick: (Result) -> Unit,
    onPlayVideo: (String) -> Unit
) {


    val apiState by viewModel.recipeApiState.collectAsState()

    var search by remember {
        mutableStateOf(false)
    }

    val userListItems: LazyPagingItems<Result>

    var searchValue by rememberSaveable {
        mutableStateOf("")
    }


    userListItems = if (search) {
        viewModel.user(searchValue).collectAsLazyPagingItems()
    } else {
        viewModel.user("").collectAsLazyPagingItems()
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (listview, searchView, button) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(searchView) {
                    top.linkTo(parent.top)
                }
                .wrapContentHeight()
                .padding(20.dp),
            fontSize = 24.sp,
            text = "Find your delicious recipes here",
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(searchView.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp)

                .background(Color(0xfff5f5f5))
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(5.dp)
                ),

            value = searchValue,
            onValueChange = {

                searchValue = it
                search = false
            },
            singleLine = true,
            maxLines = 1,
            trailingIcon = {
                IconButton(
                    onClick = {
                        search = true
                    }) {
                    Icon(Icons.Filled.Search, contentDescription = "null", tint = Color.Blue)
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.wrapContentHeight(),
                    fontSize = 14.sp,
                    text = "Search food or, ingredients...",
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(listview) {
                    top.linkTo(button.bottom, 5.dp)
                }
                .padding(bottom = 200.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(userListItems) { item ->
                RecipeList(result = item!!, onItemClick = onItemClick, onPlayVideo)
            }

            if (userListItems.itemCount > 0) {
                viewModel.recipeApiState.value = UiState(false)
            }


            userListItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        viewModel.recipeApiState.value = UiState(true)
                    }
                    loadState.append is LoadState.Loading -> {
//                        viewModel.recipeApiState.value = UiState(true)
                        //You can add modifier to manage load state when next response page is loading
                    }
                    loadState.append is LoadState.Error -> {
                        //You can use modifier to show error message
                    }
                }
            }
        }


        if (apiState.loading) {
            Box(modifier = Modifier.fillMaxSize()) {


                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.Black
                )

            }
        }


    }
}


