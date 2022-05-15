package com.bytee.foodrecipe.data.di

import android.content.Context
import com.bytee.foodrecipe.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesApiClient(@ApplicationContext context: Context) = ApiClient()
}