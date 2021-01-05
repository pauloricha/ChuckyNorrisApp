package br.com.example

import android.app.Application
import br.com.example.guiabolsoapptest.data.database.JokeDatabase

open class ChuckNorrisApplication : Application() {

    companion object {
        lateinit var instance: ChuckNorrisApplication
        lateinit var database: JokeDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = JokeDatabase.invoke(this)
    }
}
