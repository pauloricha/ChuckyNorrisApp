package br.com.example.guiabolsoapptest.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke")
data class JokeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "icon_url")
    val icon_url: String,
    @ColumnInfo(name = "id_joke")
    val id_joke: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "value")
    val value: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)
