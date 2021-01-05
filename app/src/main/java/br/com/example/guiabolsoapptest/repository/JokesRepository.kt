package br.com.example.guiabolsoapptest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.example.ChuckNorrisApplication
import br.com.example.guiabolsoapptest.data.database.JokeSingleton
import br.com.example.guiabolsoapptest.data.database.toJokeEntity
import br.com.example.guiabolsoapptest.data.database.toJokeList
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.model.Joke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class JokesRepository : ShareRepository() {

    private val _joke by lazy { MutableLiveData<Joke>() }
    val joke: LiveData<Joke>
        get() = _joke

    private val _jokes by lazy { MutableLiveData<List<Joke>>() }
    val jokes: LiveData<List<Joke>>
        get() = _jokes

    private val _favoriteJokes by lazy { MutableLiveData<List<Joke>>() }
    val favoriteJokes: LiveData<List<Joke>>
        get() = _favoriteJokes

    private val _favoriteJoke by lazy { MutableLiveData<Boolean>() }
    val isFavoriteJoke: LiveData<Boolean>
        get() = _favoriteJoke

    private val _isDeleted by lazy { MutableLiveData<Boolean>() }
    val isDeleted: LiveData<Boolean>
        get() = _isDeleted

    init {
        DaggerAppComponent.create().inject(this)
    }

    private fun getJoke(category: String): Disposable {
        return chuckNorrisApi.getJoke(category)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { joke ->
                    if(joke != null) {
                        _joke.postValue(joke)
                    }
                    _loading.postValue(false)
                },
                {
                    _loading.postValue(false)
                    _isError.postValue(true)
                }
            )
    }

    private fun getFavoriteJokes(): Disposable {
        _loading.postValue(true)
        return ChuckNorrisApplication.database.jokeDao()
            .getFavoriteJokes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { jokeEntityList ->
                    if (jokeEntityList != null && jokeEntityList.isNotEmpty()) {
                        JokeSingleton.favoriteJokes.clear()
                        JokeSingleton.favoriteJokes.addAll(jokeEntityList.toJokeList())
                        _loading.postValue(false)
                        _favoriteJokes.postValue(jokeEntityList.toJokeList())
                    } else {
                        _loading.postValue(false)
                        _favoriteJokes.postValue(jokeEntityList.toJokeList())
                    }
                },
                {
                    _loading.postValue(false)
                    _isError.postValue(true)
                }
            )
    }

    private fun getFoundJokes(text: String): Disposable {
        _loading.postValue(true)
        return chuckNorrisApi.getFoundJokes(text)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { foundJokes ->
                    if (foundJokes != null && foundJokes.result.isNotEmpty()) {
                        _jokes.postValue(foundJokes.result)
                    }
                    _loading.postValue(false)
                },
                {
                    _isError.postValue(true)
                    _loading.postValue(false)
                }
            )
    }

    private fun insertJoke(favoriteJoke: Joke) {
        favoriteJoke.isFavorite = true
        val jokeEntity = favoriteJoke.toJokeEntity()
        return ChuckNorrisApplication.database.jokeDao().insertJoke(jokeEntity)
            .subscribeOn(Schedulers.io())
            .subscribe ({
            },{
            }).let {
                JokeSingleton.favoriteJokes.add(favoriteJoke)
                _favoriteJoke.postValue(true)
                _jokes.postValue(JokeSingleton.favoriteJokes)
            }
    }

    private fun deleteJoke(favoriteJoke: Joke) {
        val jokeEntity = favoriteJoke.toJokeEntity()
        return ChuckNorrisApplication.database.jokeDao().deleteJoke(jokeEntity.id)
            .subscribeOn(Schedulers.io())
            .subscribe ({
            },{
            }).let {
                _isDeleted.postValue(true)
            }
    }

    fun fetchJokeFromApi(category: String): Disposable = getJoke(category)
    fun fetchFavoriteJokesFromDatabase(): Disposable = getFavoriteJokes()
    fun fetchFoundJokesFromApi(text: String): Disposable = getFoundJokes(text)
    fun insertJokeIntoDatabase(favoriteJoke: Joke) = insertJoke(favoriteJoke)
    fun deleteJokeFromDatabase(favoriteJoke: Joke) = deleteJoke(favoriteJoke)
}