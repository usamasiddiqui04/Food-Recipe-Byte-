package com.bytee.foodrecipe.domain.usecases

import com.bytee.foodrecipe.data.utils.FRThrowable
import com.bytee.foodrecipe.domain.model.FoodRecipe
import com.bytee.foodrecipe.domain.model.FoodRecipeRequest
import com.bytee.foodrecipe.domain.repositories.FoodRecipeRepository
import javax.inject.Inject
import  com.bytee.foodrecipe.data.utils.Result
import com.bytee.foodrecipe.domain.repositories.RecipeDeatilsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SimilerRecipeUseCase @Inject constructor(
    private val repository: RecipeDeatilsRepository
) {

    suspend operator fun invoke(
        foodRecipeRequest: Int
    ) : Result<FoodRecipe> {

        val response = repository.getAllRecipe(foodRecipeRequest)

        return when(response){
            is Result.Success -> {
                Result.Success(response.data)
            }
            is Result.Error -> {
                Result.Error(FRThrowable(errorMessage = "Unable to connect"))
            }
        }

    }
}