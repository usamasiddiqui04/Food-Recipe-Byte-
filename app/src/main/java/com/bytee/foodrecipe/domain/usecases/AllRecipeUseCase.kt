package com.bytee.foodrecipe.domain.usecases

import com.bytee.foodrecipe.domain.model.FoodRecipe
import com.bytee.foodrecipe.domain.model.FoodRecipeRequest
import com.bytee.foodrecipe.domain.repositories.FoodRecipeRepository
import javax.inject.Inject
import  com.bytee.foodrecipe.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AllRecipeUseCase @Inject constructor(
    private val repository: FoodRecipeRepository
) {

    suspend operator fun invoke(
        foodRecipeRequest: FoodRecipeRequest
    ) : Flow<FoodRecipe> = flow {
        emit(repository.getAllRecipe(foodRecipeRequest))
    }.flowOn(IO)
}