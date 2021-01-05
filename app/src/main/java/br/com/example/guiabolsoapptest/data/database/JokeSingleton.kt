package br.com.example.guiabolsoapptest.data.database

import br.com.example.guiabolsoapptest.model.Joke

object JokeSingleton {
    var favoriteJokes = mutableListOf<Joke>()
}