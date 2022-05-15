package com.bytee.foodrecipe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodRecipeRequest(
    val from : Int?,
    val size : Int?,
    val q : String?

)
