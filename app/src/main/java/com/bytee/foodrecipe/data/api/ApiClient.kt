package com.bytee.foodrecipe.data.api


import android.content.Context
import android.util.Log

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class ApiClient() {
    val client = HttpClient(OkHttp) {



        install(HttpTimeout) {
            requestTimeoutMillis = 2000000
            connectTimeoutMillis = 2000000
            socketTimeoutMillis = 2000000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(json).apply {  }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }


        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            header("Accept", "application/json")
            header("X-RapidAPI-Host","tasty.p.rapidapi.com")
            header("X-RapidAPI-Key" , ApiEndPoints.apiKey)

//            header("Authorization", "Client-ID wxl8gDYQvKHvTcfTzfFJ2Fy0GoSuKoJovMopdieYBvk")
        }




        engine {

        }
    }

}

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    isLenient = true // no json in response but response is html
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = true
    explicitNulls = false

}
