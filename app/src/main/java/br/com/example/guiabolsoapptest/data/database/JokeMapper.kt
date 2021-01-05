package br.com.example.guiabolsoapptest.data.database

import br.com.example.guiabolsoapptest.model.Joke

fun JokeEntity.toJoke() = Joke(
    this.id,
    this.icon_url,
    this.id_joke,
    this.url,
    this.value,
    this.isFavorite
)

fun List<JokeEntity>.toJokeList() = this.map { it.toJoke() }

fun Joke.toJokeEntity() = JokeEntity(
    id = this.id_joke,
    icon_url = this.icon_url,
    id_joke = this.id,
    url = this.url,
    value = this.value,
    isFavorite = this.isFavorite
)

fun List<Joke>.toJokeEntityList() = this.map { it.toJokeEntity() }
