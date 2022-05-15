package com.bytee.foodrecipe.domain.repositories

import com.bytee.foodrecipe.data.api.ApiClient
import com.bytee.foodrecipe.data.api.ApiEndPoints
import com.bytee.foodrecipe.data.utils.BaseNetworkUtils
import com.bytee.foodrecipe.domain.model.FoodRecipe
import com.bytee.foodrecipe.domain.model.FoodRecipeRequest
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import  com.bytee.foodrecipe.data.utils.Result
import io.ktor.client.features.*
import kotlinx.coroutines.coroutineScope

class FoodRecipeRepository @Inject constructor(
    private val apiClient: ApiClient,

    ) {
    suspend fun getAllRecipe(foodRecipeRequest: FoodRecipeRequest): FoodRecipe {
        return apiClient.client.get {
            url(
                ApiEndPoints.baseUrl + ApiEndPoints.recipeListEndPoint +
                        "?from=${foodRecipeRequest.from}" +
                        "&size=${foodRecipeRequest.size}" +
                        "&q=${foodRecipeRequest.q}"
            )
            contentType(ContentType.Application.Json)
        }
    }
}