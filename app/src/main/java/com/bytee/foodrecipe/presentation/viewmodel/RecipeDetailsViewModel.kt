package com.bytee.foodrecipe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytee.foodrecipe.data.utils.FRThrowable
import com.bytee.foodrecipe.data.utils.Result
import com.bytee.foodrecipe.data.utils.UiState
import com.bytee.foodrecipe.domain.model.FoodRecipe
import com.bytee.foodrecipe.domain.usecases.SimilerRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val useCase: SimilerRecipeUseCase
) : ViewModel() {

    var recipeApiState =
        MutableStateFlow(UiState<FoodRecipe>(loading = false, data = null, exception = null))




    fun getList(foodRecipeRequest: Int) {
        recipeApiState.value = UiState(true, data = null, exception = null)

        viewModelScope.launch {
            when (val result = useCase(foodRecipeRequest = foodRecipeRequest)) {
                is Result.Success -> {
                    recipeApiState.value = UiState(false, data = result.data, exception = null)
                }

                is Result.Error -> {
                    recipeApiState.value = UiState(
                        loading = false,
                        data = null,
                        exception = FRThrowable(
                            errorCode = 400,
                            errorMessage = result.throwable.errorMessage,
                            errorTitle = ""
                        )
                    )
                }
            }
        }

    }




}