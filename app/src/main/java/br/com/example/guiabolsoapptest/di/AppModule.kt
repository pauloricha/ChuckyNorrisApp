package br.com.example.guiabolsoapptest.di

import br.com.example.guiabolsoapptest.data.network.ChuckNorrisApi
import br.com.example.guiabolsoapptest.data.network.ChuckNorrisApiService
import br.com.example.guiabolsoapptest.model.Joke
import br.com.example.guiabolsoapptest.repository.CategoriesRepository
import br.com.example.guiabolsoapptest.repository.JokesRepository
import br.com.example.guiabolsoapptest.repository.ShareRepository
import br.com.example.guiabolsoapptest.view.categories.CategoryAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): ChuckNorrisApi = ChuckNorrisApiService().getClient()

    //Repositories
    @Provides
    fun provideShareRespository() = ShareRepository()
    @Provides
    fun provideCategoriesRepository() = CategoriesRepository()
    @Provides
    fun provideJokesRepository() = JokesRepository()

    //Models
    @Provides
    fun provideCategories() = ArrayList<String>()
    @Provides
    fun provideJokes() = ArrayList<Joke>()
    @Provides
    fun provideIsFavoriteScreen() = Boolean

    //Adapters
    @Provides
    fun provideCategoryAdapter(categories: ArrayList<String>): CategoryAdapter =
        CategoryAdapter(categories)
}