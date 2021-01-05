package br.com.example.guiabolsoapptest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.example.guiabolsoapptest.internal.DATABASE_NAME

@Database(entities = [JokeEntity::class], version = 2)
abstract class JokeDatabase: RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    companion object {
        private var instance: JokeDatabase? = null

        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                JokeDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}
