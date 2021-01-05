package br.com.example.guiabolsoapptest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.example.guiabolsoapptest.data.network.ChuckNorrisApi
import javax.inject.Inject

open class ShareRepository {

    @Inject lateinit var chuckNorrisApi: ChuckNorrisApi

    val _loading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean>
        get() = _loading

    val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError
}