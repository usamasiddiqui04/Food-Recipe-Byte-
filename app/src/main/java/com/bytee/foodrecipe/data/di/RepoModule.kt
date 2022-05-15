package com.bytee.foodrecipe.data.di

import com.bytee.foodrecipe.data.api.ApiClient
import com.bytee.foodrecipe.domain.repositories.FoodRecipeRepository
import com.bytee.foodrecipe.domain.repositories.RecipeDeatilsRepository
import com.bytee.foodrecipe.domain.usecases.AllRecipeUseCase
import com.bytee.foodrecipe.domain.usecases.SimilerRecipeUseCase
import com.bytee.foodrecipe.presentation.viewmodel.FoodRecipeViewModel
import com.bytee.foodrecipe.presentation.viewmodel.RecipeDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {


    @Singleton
    @Provides
    fun provideFoodRecipeUseCase(foodRecipeRepository: FoodRecipeRepository) = AllRecipeUseCase(repository = foodRecipeRepository)


    @Singleton
    @Provides
    fun provideSimilerUserCase(foodRecipeRepository: RecipeDeatilsRepository) = SimilerRecipeUseCase(repository = foodRecipeRepository)

    @Singleton
    @Provides
    fun provideFoodViewModel(useCase: AllRecipeUseCase , repository: FoodRecipeRepository) = FoodRecipeViewModel(useCase = useCase , repository = repository)


    @Singleton
    @Provides
    fun provideSimilerViewModel(useCase: SimilerRecipeUseCase) = RecipeDetailsViewModel(useCase = useCase)


    @Singleton
    @Provides
    fun provideSimierApiClient(apiClient: ApiClient) = RecipeDeatilsRepository(
        apiClient = apiClient,
    )

    @Singleton
    @Provides
    fun providesApiClient(apiClient: ApiClient) = FoodRecipeRepository(
        apiClient = apiClient,
    )
}