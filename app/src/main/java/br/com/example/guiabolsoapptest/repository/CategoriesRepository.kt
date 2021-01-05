package br.com.example.guiabolsoapptest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CategoriesRepository : ShareRepository() {

    private val _categories by lazy { MutableLiveData<List<String>>() }
    val categories: LiveData<List<String>>
        get() = _categories

    init {
        DaggerAppComponent.create().inject(this)
    }

    private fun getCategories(): Disposable {
        _loading.postValue(true)
        return chuckNorrisApi.getCategories()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { categories ->
                    if (categories != null && categories.isNotEmpty()) {
                        _categories.postValue(categories)
                    }
                    _loading.postValue(false)
                },
                {
                    _isError.postValue(true)
                    _loading.postValue(false)
                }
            )
    }

    fun fetchCategoriesFromDatabase(): Disposable = getCategories()
}