package br.com.example.guiabolsoapptest.model

import java.io.Serializable

data class FoundJokes(
    var total: String,
    var result: List<Joke>
) : Serializable