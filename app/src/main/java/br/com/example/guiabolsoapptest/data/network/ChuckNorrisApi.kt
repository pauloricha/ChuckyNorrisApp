package br.com.example.guiabolsoapptest.data.network

import br.com.example.guiabolsoapptest.model.FoundJokes
import br.com.example.guiabolsoapptest.model.Joke
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {

    @GET("jokes/categories/")
    fun getCategories(): Flowable<List<String>>

    @GET("jokes/random")
    fun getJoke(
        @Query("category") category: String): Flowable<Joke>

    @GET("jokes/search")
    fun getFoundJokes(
        @Query("query") query: String): Flowable<FoundJokes>
}