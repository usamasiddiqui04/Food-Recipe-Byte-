package com.bytee.foodrecipe.domain.repositories

import com.bytee.foodrecipe.data.api.ApiClient
import com.bytee.foodrecipe.data.api.ApiEndPoints
import com.bytee.foodrecipe.data.utils.FRThrowable
import com.bytee.foodrecipe.data.utils.Result
import com.bytee.foodrecipe.domain.model.FoodRecipe
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RecipeDeatilsRepository @Inject constructor(
    private val apiClient: ApiClient,

    ) {
    suspend fun getAllRecipe(id: Int): Result<FoodRecipe> {


        val res =  apiClient.client.get<FoodRecipe>(
            urlString = ApiEndPoints.baseUrl + ApiEndPoints.listSimilarities +
                    "?recipe_id=${id}"
        ) {
            contentType(ContentType.Application.Json)
        }

        return if (res.count!! > 0) {
            Result.Success(res)
        } else {
            Result.Error(
                FRThrowable(
                    errorCode = 400,
                    errorMessage = "Unable to connect"
                )
            )
        }
    }
}