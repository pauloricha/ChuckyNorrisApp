package br.com.example.guiabolsoapptest.model

import java.io.Serializable

data class Joke(
    var id_joke: Int,
    var icon_url: String,
    var id: String,
    var url: String,
    var value: String,
    var isFavorite: Boolean = false
) : Serializable