package com.bytee.foodrecipe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.bytee.foodrecipe.data.utils.UiState
import com.bytee.foodrecipe.domain.model.FoodRecipe
import com.bytee.foodrecipe.domain.model.Result
import com.bytee.foodrecipe.domain.repositories.FoodRecipeRepository
import com.bytee.foodrecipe.domain.usecases.AllRecipeUseCase
import com.bytee.foodrecipe.presentation.pagination.RecipePagination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class FoodRecipeViewModel @Inject constructor(
    private val useCase: AllRecipeUseCase,
    private val repository: FoodRecipeRepository
): ViewModel() {

    var recipeApiState =
        MutableStateFlow(UiState<FoodRecipe>(loading = false, data = null, exception = null))


    fun user(q : String): Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        RecipePagination(repository = repository , q)
    }.flow.cachedIn(viewModelScope)


}