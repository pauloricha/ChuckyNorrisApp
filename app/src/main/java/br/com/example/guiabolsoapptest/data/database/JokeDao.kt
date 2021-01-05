package br.com.example.guiabolsoapptest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(jokeEntity: JokeEntity) : Completable

    @Query("SELECT * from joke")
    fun getFavoriteJokes(): Single<List<JokeEntity>>

    @Query("DELETE FROM joke WHERE id = :id")
    fun deleteJoke(id: Int) : Completable

    @Query("DELETE FROM joke")
    fun deleteAll()
}
