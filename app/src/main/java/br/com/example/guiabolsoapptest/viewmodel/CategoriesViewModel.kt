package br.com.example.guiabolsoapptest.viewmodel

import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.repository.CategoriesRepository
import br.com.example.guiabolsoapptest.view.ShareViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CategoriesViewModel : ShareViewModel() {

    @Inject
    lateinit var categoriesRepository: CategoriesRepository

    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        DaggerAppComponent.create().inject(this)
        compositeDisposable.add(categoriesRepository.fetchCategoriesFromDatabase())
    }
}