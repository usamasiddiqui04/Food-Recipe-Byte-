package com.bytee.foodrecipe.data.api

abstract class ApiEndPoints {

    companion object{
        const val baseUrl = "https://tasty.p.rapidapi.com/"
        const val apiKey = "b9604a3b0dmsh1960691a295845ep1ca6e5jsn876b112e8a8d"
        const val recipeListEndPoint =  "recipes/list/"
        const val listSimilarities = "recipes/list-similarities/"
    }
}