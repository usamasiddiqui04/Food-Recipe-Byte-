package com.bytee.foodrecipe.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodRecipe(
    val count: Int?,
    val results: List<Result>?
)