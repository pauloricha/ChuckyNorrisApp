package br.com.example.guiabolsoapptest.view

import androidx.lifecycle.ViewModel
import br.com.example.guiabolsoapptest.di.DaggerAppComponent
import br.com.example.guiabolsoapptest.repository.ShareRepository
import javax.inject.Inject

open class ShareViewModel : ViewModel() {

    @Inject
    lateinit var repository: ShareRepository

    init {
        DaggerAppComponent.create().inject(this)
    }
}