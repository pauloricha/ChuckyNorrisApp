package br.com.example.guiabolsoapptest.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.internal.BUNDLE_CATEGORY
import br.com.example.guiabolsoapptest.internal.BUNDLE_JOKE
import br.com.example.guiabolsoapptest.internal.BUNDLE_TEXT_TO_SEARCH
import br.com.example.guiabolsoapptest.model.Joke
import br.com.example.guiabolsoapptest.repository.JokesRepository
import br.com.example.guiabolsoapptest.view.ShareViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class JokesViewModel : ShareViewModel() {

    var category = MutableLiveData<String?>()
    var textToSearch = MutableLiveData<String?>()
    private val isFavoriteScreen: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private var joke: Joke? = null

    @Inject
    lateinit var jokesRepository: JokesRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun checkArguments(arguments: Bundle?) {
        if(arguments != null) {
            if(arguments.containsKey(BUNDLE_CATEGORY)){
                category.value = arguments.getString(BUNDLE_CATEGORY).toString()
            } else if(arguments.containsKey(BUNDLE_TEXT_TO_SEARCH)){
                isFavoriteScreen.value = false
                textToSearch.value = arguments.getString(BUNDLE_TEXT_TO_SEARCH).toString()
            } else if(arguments.containsKey(BUNDLE_JOKE)) {
                joke = arguments.getSerializable(BUNDLE_JOKE) as Joke
            } else {
                isFavoriteScreen.value = true
            }
        }
    }

    fun hasJoke(): Boolean? = joke != null

    fun isFavoriteJoke(): Boolean? = joke!!.isFavorite

    fun getCategory(): String? = category.value

    fun getJoke(): Joke? = joke

    fun setJoke(joke: Joke?) {
        this.joke = joke
    }

    fun isFavoriteScreen(): Boolean? = isFavoriteScreen.value

    fun getTextToSearch(): String? = textToSearch.value

    fun fetchJokeFromApi(category: String) {
        jokesRepository.fetchJokeFromApi(category)
    }

    fun fetchFavoriteJokesFromDatabase() {
        compositeDisposable.add(jokesRepository.fetchFavoriteJokesFromDatabase())
    }

    fun fetchFoundJokesFromApi(textToSearch: String) {
        compositeDisposable.add(jokesRepository.fetchFoundJokesFromApi(textToSearch))
    }

    fun deleteJokeFromDatabase(favoriteJoke: Joke) {
        jokesRepository.deleteJokeFromDatabase(favoriteJoke)
    }

    fun insertJokeIntoDatabase(favoriteJoke: Joke) {
        jokesRepository.insertJokeIntoDatabase(favoriteJoke)
    }
}