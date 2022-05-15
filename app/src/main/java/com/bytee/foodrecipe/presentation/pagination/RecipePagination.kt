package com.bytee.foodrecipe.presentation.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.bytee.foodrecipe.domain.model.FoodRecipeRequest
import com.bytee.foodrecipe.domain.model.Result
import com.bytee.foodrecipe.domain.repositories.FoodRecipeRepository
import io.ktor.utils.io.errors.*

class RecipePagination(val repository: FoodRecipeRepository, val q: String) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try {
            val nextPage = params.key ?: 1
            val userList =
                repository.getAllRecipe(FoodRecipeRequest(size = nextPage + 20, from = nextPage, q = q))
            LoadResult.Page(
                data = userList.results!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.results.isEmpty()) null else nextPage + 20
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}